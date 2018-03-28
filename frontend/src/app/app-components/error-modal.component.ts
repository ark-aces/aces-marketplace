import { Component, Input } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap';
import { BsModalRef } from 'ngx-bootstrap';

@Component({
  selector: 'app-error-modal',
  template: `
    <div class="modal-header alert-danger">
      <h4 class="modal-title pull-left alert-danger">
        <span class="glyphicon glyphicon-alert alert-icon" aria-hidden="true"></span>
        {{title}}
      </h4>
      <button type="button" class="close pull-right" aria-label="Close" (click)="close()">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      {{message}}
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-default" (click)="close()">Close</button>
    </div>
  `,
  providers: [BsModalService]
})

export class ErrorModalComponent {

  @Input() title = 'Unexpected Error';
  @Input() message = 'An unexpected error occurred. Please try again later.';
  @Input() reloadOnClose = false;

  constructor(public bsModalRef: BsModalRef) { }

  close() {
    this.bsModalRef.hide();
    if (this.reloadOnClose) {
      location.reload();
    }
  }

}
