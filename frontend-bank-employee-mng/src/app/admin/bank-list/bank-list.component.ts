import { Component, inject, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';
import { Bank } from '../bank';

@Component({
  selector: 'app-bank-list',
  standalone: true,
  imports: [],
  templateUrl: './bank-list.component.html',
  styleUrl: './bank-list.component.css'
})
export class BankListComponent implements OnInit {

  banks: Bank[] = [];
  private adminService = inject(AdminService);

  ngOnInit(): void {
    this.adminService.getAllBranches().subscribe(data => {
      this.banks = data;
    });
  }

}
