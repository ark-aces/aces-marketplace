import { Component, OnInit } from '@angular/core';
import { ApiClient, Capacity, Service, ServiceCategory } from '../api-client/api-client.component';
import { ErrorModalService } from '../app-components/error-modal-service.compoennt';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Router } from '@angular/router';
import { Subject } from 'rxjs/Subject';
import 'rxjs/add/operator/debounceTime';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.scss']
})
export class ServicesComponent implements OnInit {

  isLoading: boolean = true;
  isLoadingChanged: Subject<boolean> = new Subject<boolean>();
  services: Array<Service> = [];
  search: String;
  searchChanged: Subject<String> = new Subject<String>();
  isFiltersDivExpanded: boolean = false;
  categories: Array<ServiceCategory> = [];
  categoriesChanged: Subject<any> = new Subject<any>();
  maxFlatFee: number;
  maxFlatFeeChanged: Subject<number> = new Subject<number>();
  maxPercentFee: number;
  maxPercentFeeChanged: Subject<number> = new Subject<number>();
  minCapacity: String;
  minCapacityChanged: Subject<String> = new Subject<String>();
  minCapacities: Array<Capacity> = [];
  sortProperties: Array<Object> = ['Relevance', 'Percent Fee', 'Flat Fee'];
  sortPropertyChanged: Subject<String> = new Subject<String>();
  sortDirections: Array<Object> = ['Ascending', 'Descending'];
  sortDirectionChanged: Subject<String> = new Subject<String>();
  sort: any = { property: 'Relevance', direction: 'Ascending' };
  debounceTime: number = 500;

  constructor(
    private apiClient: ApiClient,
    private errorModalService: ErrorModalService,
    private loadingSpinner: NgxSpinnerService,
    private activeRoute: ActivatedRoute,
    private router: Router
  ) {
    this.apiClient.getServiceCategories().subscribe((categories: any) => {
      this.categories = categories.sort((category1, category2) => category1.name.localeCompare(category2.name));
      this.loadQueryParams();
      this.updateSearch();
    }, error => {
      console.log(error);
      this.errorModalService.showDefaultError();
    });
  }

  resetSearch() {
    this.search = null;
    this.categories.forEach(category => category.isSelected = false);
    this.maxFlatFee = null;
    this.maxPercentFee = null;
    this.minCapacity = null;
    this.minCapacities = [];
    this.sort = { property: 'Relevance', direction: 'Ascending' };
    this.updateSearch();
  }

  ngOnInit() {
    this.isLoadingChanged
      .debounceTime(this.debounceTime)
      .distinctUntilChanged()
      .subscribe(isLoading => {
        if (isLoading) {
          this.loadingSpinner.show();
        } else {
          this.loadingSpinner.hide();
        }
        this.isLoading = isLoading;
      });
    this.searchChanged
      .debounceTime(this.debounceTime)
      .distinctUntilChanged()
      .subscribe(search => {
        this.search = search;
        this.updateSearch();
      });
    this.categoriesChanged
      .debounceTime(this.debounceTime)
      .distinctUntilChanged()
      .subscribe(category => {
        this.categories[category.name] = category.isSelected;
        this.updateSearch();
      });
    this.maxFlatFeeChanged
      .debounceTime(this.debounceTime)
      .distinctUntilChanged()
      .subscribe(maxFlatFee => {
        this.maxFlatFee = maxFlatFee;
        this.updateSearch();
      });
    this.maxPercentFeeChanged
      .debounceTime(this.debounceTime)
      .distinctUntilChanged()
      .subscribe(maxPercentFee => {
        this.maxPercentFee = maxPercentFee;
        this.updateSearch();
      });
    this.minCapacityChanged
      .subscribe(minCapacity => {
        if (minCapacity.trim().match('\\d \\w')) {
          let splitMinCapacity = minCapacity.trim().split(' ');
          let newMinCapacity = {
            value: +splitMinCapacity[0],
            unit: splitMinCapacity[1].toUpperCase()
          };
          if (!this.minCapacities.some(mc => mc.unit === newMinCapacity.unit)) {
            this.minCapacities.push(newMinCapacity);
            this.updateSearch();
            this.minCapacity = null;
          }
        }
      });
    this.sortPropertyChanged
      .debounceTime(this.debounceTime)
      .distinctUntilChanged()
      .subscribe(property => {
        this.sort.property = property;
        this.updateSearch();
      });
    this.sortDirectionChanged
      .debounceTime(this.debounceTime)
      .distinctUntilChanged()
      .subscribe(direction => {
        this.sort.direction = direction;
        this.updateSearch();
      });
  }

  loadQueryParams() {
    let queryParams = this.activeRoute.snapshot.queryParams;
    if (queryParams.search) {
      this.search = queryParams.search;
    }
    if (queryParams.categories && queryParams.categories.length) {
      if (queryParams.categories instanceof Array) {
        this.categories.forEach(category => {
          if (queryParams.categories.includes(category.name)) {
            category.isSelected = true;
          }
        });
      } else {
        this.categories.forEach(category => {
          if (category.name === queryParams.categories) {
            category.isSelected = true;
          }
        });
      }
    }
    if (queryParams.maxFlatFee) {
      this.maxFlatFee = queryParams.maxFlatFee;
    }
    if (queryParams.maxPercentFee) {
      this.maxPercentFee = queryParams.maxPercentFee;
    }
    if (queryParams.minCapacities && queryParams.minCapacities.length) {
      if (queryParams.minCapacities instanceof Array) {
        queryParams.minCapacities.forEach(minCapacity => {
          let splitMinCapacity = minCapacity.split(' ');
          if (splitMinCapacity.length == 2) {
            this.minCapacities.push({
              value: +splitMinCapacity[0],
              unit: splitMinCapacity[1]
            });
          }
        });
      } else {
        let splitMinCapacity = queryParams.minCapacities.split(' ');
        if (splitMinCapacity.length == 2) {
          this.minCapacities.push({
            value: +splitMinCapacity[0],
            unit: splitMinCapacity[1]
          });
        }
      }
    }
    if (queryParams.sort) {
      let splitSort = queryParams.sort.split(',');
      if (splitSort.length == 2) {
        this.sort.property = this.uncamelize(splitSort[0]);
        let lowercaseDirection = splitSort[1].toLowerCase();
        if (lowercaseDirection === 'asc') {
          this.sort.direction = 'Ascending';
        } else if (lowercaseDirection === 'desc') {
          this.sort.direction = 'Descending';
        }
      }
    }
  }

  removeMinCapacity(minCapacityToRemove) {
    this.minCapacities = this.minCapacities.filter(minCapacity => minCapacity.value !== minCapacityToRemove.value || minCapacity.unit !== minCapacityToRemove.unit);
    this.updateSearch();
  }

  updateSearch() {
    this.isLoadingChanged.next(true);
    this.router.navigate([], {
      relativeTo: this.activeRoute,
      queryParams: this.getQueryParams()
    });
    this.apiClient.getServices(this.getQueryParams()).subscribe(response => {
      this.services = response.items;
      this.isLoadingChanged.next(false);
    }, error => {
      console.log(error);
      this.errorModalService.showDefaultError();
      this.isLoadingChanged.next(false);
    });
  }

  getQueryParams() {
    let queryParams: any = {};
    if (this.search) {
      queryParams.search = this.search;
    }
    if (this.categories && this.categories.filter(category => category.isSelected).length) {
      queryParams.categories = this.categories.filter(category => category.isSelected).map(category => category.name);
    }
    if (this.maxFlatFee) {
      queryParams.maxFlatFee = this.maxFlatFee;
    }
    if (this.maxPercentFee) {
      queryParams.maxPercentFee = this.maxPercentFee;
    }
    if (this.minCapacities && this.minCapacities.length) {
      queryParams.minCapacities = this.minCapacities.map(minCapacity => `${minCapacity.value} ${minCapacity.unit}`);
    }
    if (this.sort && this.sort.property && this.sort.property !== 'Relevance' && this.sort.direction) {
      queryParams.sort = `${this.camelize(this.sort.property)},${this.sort.direction === 'Ascending' ? 'ASC' : 'DESC'}`;
    }
    return queryParams;
  }

  camelize(str) {
    return str.replace(/(?:^\w|[A-Z]|\b\w)/g, (letter, index) => {
      return index == 0 ? letter.toLowerCase() : letter.toUpperCase();
    }).replace(/\s+/g, '');
  }

  uncamelize(str) {
    return str.charAt(0).toUpperCase() + str.replace(/([A-Z])/g, " $1").slice(1);
  }
}
