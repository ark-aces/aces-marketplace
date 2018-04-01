import {Component, Input, OnInit} from '@angular/core';
import {ApiClient, FieldError} from '../api-client/api-client.component';
import {Router} from '@angular/router';
import {ErrorModalService} from '../app-components/error-modal-service.compoennt';

class FormInput {
  public id: string;
  label: string;
  hasErrors: boolean;
  isOptional: boolean;
  placeholder: string;
  public value: string;
  type: string;
  description: string;
  fieldErrors: Array<FieldError>;
}

@Component({
  selector: 'app-service-form',
  templateUrl: './service-form.component.html'
})
export class ServiceFormComponent implements OnInit {

  constructor(private apiClient: ApiClient, private router: Router, private errorModalService: ErrorModalService) {}

  @Input()
  serviceInfo;

  @Input()
  serviceId;

  isSubmitted = false;
  hasErrors = false;

  formInputs: Array<FormInput> = [];

  ngOnInit(): void {
    const properties = this.serviceInfo.inputSchema.properties;
    const requiredKeys = this.serviceInfo.inputSchema.required;

    for (const key in properties) {
      if (properties.hasOwnProperty(key)) {
        const property = properties[key];

        let label = key;
        if (property['title']) {
          label = property['title'];
        }

        const isOptional = key in requiredKeys;

        let type = 'string';
        if (property['format'] && property['format'] === 'text') {
          type = 'text';
        }

        let description = '';
        if (property['description']) {
          description = property['description'];
        }

        const formInput: FormInput = {
          id: key,
          label: label,
          isOptional: isOptional,
          placeholder: '',
          value: '',
          type: type,
          description: description,
          hasErrors: false,
          fieldErrors: []
        };
        this.formInputs.push(formInput);
      }
    }
  }

  onSubmit() {
    this.isSubmitted = true;

    const args = {};
    this.formInputs.forEach(formInput => {
      args[formInput.id] = formInput.value;
    });

    this.apiClient.createContract(this.serviceId, {
        arguments: args
      })
      .subscribe(
        data => {
          this.router.navigate(['/contracts', data.id]);
        },
        error => {
          // todo: report server timeout errors more specifically
          console.log(error);
          this.errorModalService.showDefaultError();
          this.hasErrors = true;
          this.isSubmitted = false;
        }
      );
  }

}
