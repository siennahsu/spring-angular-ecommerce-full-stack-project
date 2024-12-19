import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderHistory } from 'src/app/common/order-history';

@Injectable({
  providedIn: 'root'
})
export class OrderHistoryService {

  private orderUrl = 'http://localhost:8080/api/orders';

  constructor(private httpClient: HttpClient) { }

  getOrderHistory(theEmail: string): Observable<GetResponseOrderHistory> {

    // need to build URL based on the customer email
    const orderHistoryUrl = `${this.orderUrl}/search/findByCustomerEmailOrderByDateCreatedDesc?email=${theEmail}`;

    return this.httpClient.get<GetResponseOrderHistory>(orderHistoryUrl);
  }
}

interface GetResponseOrderHistory {
  _embedded: {
    orders: OrderHistory[];
  }
}


// // order.service.ts
// import { Injectable } from '@angular/core';
// import { HttpClient, HttpParams } from '@angular/common/http';
// import { Observable } from 'rxjs';

// @Injectable({
//   providedIn: 'root'
// })
// export class OrderHistoryService {

//   private baseUrl = 'http://localhost:8080/order-history';

//   constructor(private http: HttpClient) { }

//   getUserOrders(email: string, page: number = 0, size: number = 10): Observable<any> {
//     const params = new HttpParams()
//       .set('email', email)
//       .set('page', page.toString())
//       .set('size', size.toString());

//     return this.http.get<any>(`${this.baseUrl}/order-history/orders`, { params });
//   }
// }
