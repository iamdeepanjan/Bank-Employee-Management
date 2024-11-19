import { Bank } from "./bank";

export class Employee {
    id!: number;
    empId!: string;
    name!: string;
    email!: string;
    job!: string;
    role!: string;
    password!: string;
    mobileNo!: string;
    address!: string;
    bank!: Bank;
    status!: boolean;
}
