import { TestBed } from '@angular/core/testing';

import { GeniusServiceService } from './genius-service.service';

describe('GeniusServiceService', () => {
  let service: GeniusServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GeniusServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
