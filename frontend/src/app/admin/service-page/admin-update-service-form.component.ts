import {Component, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import {ApiClient, FieldError, ValidationError} from '../../api-client/api-client.component';
import {HttpErrorResponse} from '@angular/common/http';
import {ErrorModalService} from '../../app-components/error-modal-service.compoennt';

class UpdateServiceModel {
  label: string;
  url: string;
  categoryPids: Array<number>;
  isTestnet: boolean;
}

@Component({
  selector: 'app-admin-update-service-form',
  templateUrl: './admin-update-service-form.component.html',
})
export class AdminUpdateServiceFormComponent implements OnInit {

  @Input()
  service;

  @Input()
  serviceCategories;

  @Output()
  serviceChange = new EventEmitter();

  selectedServiceCategoryIds: Array<number> = [];

  isSubmitted = false;
  isSuccess = false;
  hasErrors = false;
  errorMessage: string;

  model = new UpdateServiceModel();

  labelHasError = false;
  labelFieldErrors: Array<FieldError> = [];

  urlHasError = false;
  urlFieldErrors: Array<FieldError> = [];

  constructor(
    private router: Router,
    private apiClient: ApiClient,
    private errorModalService: ErrorModalService
  ) {
  }

  ngOnInit() {
    this.updateModel();
  }

  updateModel() {
    this.model.label = this.service.label;
    this.model.url = this.service.url;
    this.model.isTestnet = this.service.isTestnet;

    this.service.serviceCategories.forEach(category => {
      this.selectedServiceCategoryIds.push(category.id);
    });
  }

  onSubmit() {
    this.labelHasError = false;
    this.labelFieldErrors = [];
    this.urlHasError = false;
    this.urlFieldErrors = [];
    this.errorMessage = null;
    this.isSuccess = false;
    this.hasErrors = false;

    this.isSubmitted = true;

    this.apiClient
      .updateService(this.service.id, {
        label: this.model.label,
        url: this.model.url,
        categoryPids: this.selectedServiceCategoryIds,
        isTestnet: this.model.isTestnet
      })
      .subscribe(
        result => {
          console.log(result);
          this.service = result;
          this.updateModel();
          this.serviceChange.emit(this.service);
          this.isSubmitted = false;
          this.isSuccess = true;
          window.scrollTo(0, 0);
        },
        (response: HttpErrorResponse)  => {
          if (response.status === 400) {
            this.hasErrors = true;
            this.errorMessage = response.error.message;
            const validationError = <ValidationError> response.error;
            if (validationError.fieldErrors) {
              for (const fieldError of validationError.fieldErrors) {
                if (fieldError.field === 'label') {
                  this.labelHasError = true;
                  this.labelFieldErrors.push(fieldError);
                }
                if (fieldError.field === 'url') {
                  this.urlHasError = true;
                  this.urlFieldErrors.push(fieldError);
                }
              }
            }
          } else {
            console.log(response);
            this.errorModalService.showDefaultError();
          }
          this.isSuccess = false;
          this.isSubmitted = false;
        }
      );
  }

  categoryChanged(serviceCategory, checked) {
    if (checked) {
      this.addCategory(serviceCategory.id);
    } else {
      this.removeCategory(serviceCategory.id);
    }
  }

  addCategory(id: number) {
    const index = this.selectedServiceCategoryIds.indexOf(id, 0);
    if (index <= -1) {
      this.selectedServiceCategoryIds.push(id);
    }
    console.log(this.selectedServiceCategoryIds);
  }

  removeCategory(id: number) {
    const index = this.selectedServiceCategoryIds.indexOf(id, 0);
    if (index > -1) {
      this.selectedServiceCategoryIds.splice(index, 1);
    }
  }

  isSelected(category) {
    return this.selectedServiceCategoryIds.indexOf(category.id) !== -1;
  }

}
