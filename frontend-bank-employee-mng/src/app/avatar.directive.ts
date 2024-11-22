import { Directive, ElementRef, Input, OnInit } from '@angular/core';

@Directive({
  selector: '[appAvatar]',
  standalone: true
})
export class AvatarDirective implements OnInit {

  @Input('appAvatar') employeeName!: string;
 
  constructor(private el: ElementRef) { }
 
  ngOnInit() {
    const parts = this.employeeName.split(' ');
    const initials = (parts[0][0] + parts[parts.length - 1][0]).toUpperCase();
    this.el.nativeElement.textContent = initials;
  }

}
