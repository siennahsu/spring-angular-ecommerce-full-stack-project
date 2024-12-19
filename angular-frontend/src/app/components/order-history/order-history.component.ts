import { Component, OnInit } from '@angular/core';
import { OrderHistory } from '../../common/order-history';
import { OrderHistoryService } from '../../services/order-history.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {

  orderHistoryList: OrderHistory[] = [];
  storage: Storage = sessionStorage;
  userEmail: string | null = null;

  constructor(private orderHistoryService: OrderHistoryService, private authService: AuthService) { }

  ngOnInit(): void {
    this.userEmail = sessionStorage.getItem('userEmail')
    this.handleOrderHistory();
  }

  handleOrderHistory() {
    // Check if userEmail is null or undefined
    const theEmail = this.userEmail;
  
    if (theEmail) {
      this.orderHistoryService.getOrderHistory(theEmail).subscribe(
        data => {
          this.orderHistoryList = data._embedded.orders;
        },
        error => {
          console.error('Error fetching order history', error);
        }
      );
    } else {
      console.log("Cannot find user email", this.userEmail); // Debugging the value of userEmail
    }
  }

}