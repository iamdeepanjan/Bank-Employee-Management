export class JwtResponse {
    accessToken!: string;
    tokenType = "Bearer";
    id!: number;
    username!: string;
    email!: string;
    roles!: string[];
}
