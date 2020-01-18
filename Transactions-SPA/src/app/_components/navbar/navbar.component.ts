import { Component, OnInit, Input } from '@angular/core';
import { MenuItem } from 'primeng/api/menuitem';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/_services/auth.service';

@Component({
	selector: 'navbar',
	templateUrl: './navbar.component.html',
	styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

	items: MenuItem[];
	activeItem: MenuItem;

	constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService) { }

	ngOnInit() {
		this.items = [
			{ label: 'All Transactions', icon: 'pi pi-id-card' },
			{ label: 'Completed Transactions', icon: 'pi pi-check-circle' },
			{ label: 'Canceled Transactions', icon: 'pi pi-minus-circle' },
			{ label: 'Logout', icon: 'pi pi-sign-out' }
		];

		this.route.data.subscribe( params => {
			if ('state' in params) {
				if (params['state'] == 'completed') {
					this.activeItem = this.items[1];
				}
				if (params['state'] == 'cancelled') {
					this.activeItem = this.items[2];
				}
			}			
			else {
				this.activeItem = this.items[0];
			}
		  });
	}

	navigate(tab) {
		const selectedTab = tab.activeItem.label;
		if (selectedTab == "All Transactions")
			this.router.navigate(['/transactions'])
		else if (selectedTab == "Completed Transactions")
			this.router.navigate(['/transactions/completed'])
		else if (selectedTab == "Canceled Transactions")
			this.router.navigate(['/transactions/cancelled'])
		else if (selectedTab == "Logout") {
			var logout = confirm("Are you sure you want to logout?");
			if (logout) {
				this.authService.logout();
				this.router.navigate(['/login']);
			}
		}
	}

}
