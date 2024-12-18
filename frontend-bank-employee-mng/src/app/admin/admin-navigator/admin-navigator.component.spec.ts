import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminNavigatorComponent } from './admin-navigator.component';

describe('AdminNavigatorComponent', () => {
  let component: AdminNavigatorComponent;
  let fixture: ComponentFixture<AdminNavigatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminNavigatorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminNavigatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
