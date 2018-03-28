import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {ErrorModalComponent} from './error-modal.component';
import {Injectable} from '@angular/core';

@Injectable()
export class ErrorModalService {

  modalRef: BsModalRef;

  constructor(private modalService: BsModalService) {
  }

  public showError(title?: string, message?: string) {
    this.modalRef = this.modalService.show(ErrorModalComponent, {
      animated: true,
      keyboard: true,
      backdrop: true,
      ignoreBackdropClick: false,
      initialState: {
        'title': title,
        'message': message
      }
    });
  }
}
