import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserNavigatorComponent } from './user-navigator.component';

describe('UserNavigatorComponent', () => {
  let component: UserNavigatorComponent;
  let fixture: ComponentFixture<UserNavigatorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserNavigatorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserNavigatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
