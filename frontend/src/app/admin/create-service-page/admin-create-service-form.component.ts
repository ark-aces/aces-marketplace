import {Component, Inject, Input} from '@angular/core';
import {Router} from '@angular/router';
import {ApiClient, FieldError, Service, ValidationError} from '../../api-client/api-client.component';
import {HttpErrorResponse} from '@angular/common/http';
import {ErrorModalService} from '../../app-components/error-modal-service.compoennt';

class CreateServiceModel {
  label: string;
  url: string;
  categoryPids: Array<number>;
  isTestnet: boolean;
}

@Component({
  selector: 'app-admin-create-service-form',
  templateUrl: './admin-create-service-form.component.html',
})
export class AdminCreateServiceFormComponent {

  @Input()
  serviceCategories;

  selectedServiceCategoryIds: Array<number> = [];

  isSubmitted = false;
  isSuccess = false;
  hasErrors = false;
  errorMessage: string;

  model = new CreateServiceModel();

  labelHasError = false;
  labelFieldErrors: Array<FieldError> = [];

  urlHasError = false;
  urlFieldErrors: Array<FieldError> = [];

  createdId: string;

  constructor(
    private router: Router,
    private apiClient: ApiClient,
    private errorModalService: ErrorModalService
  ) {
  }

  onSubmit() {
    this.labelHasError = false;
    this.labelFieldErrors = [];
    this.urlHasError = false;
    this.urlFieldErrors = [];
    this.errorMessage = null;

    this.isSubmitted = true;

    this.apiClient
      .postService({
        label: this.model.label,
        url: this.model.url,
        categoryPids: this.selectedServiceCategoryIds,
        isTestnet: this.model.isTestnet
      })
      .subscribe(
        (result: Service) => {
          this.createdId = result.id;
          this.isSuccess = true;
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

          this.isSubmitted = false;
          window.scrollTo(0, 0);
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
  }

  removeCategory(id: number) {
    const index = this.selectedServiceCategoryIds.indexOf(id, 0);
    if (index > -1) {
      this.selectedServiceCategoryIds.splice(index, 1);
    }
  }

}
