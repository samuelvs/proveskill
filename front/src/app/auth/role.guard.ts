import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(
    public authService: AuthService,
    public router: Router
  ) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      const user = this.authService?.currentUser;
      console.log(user);

      if (state.url.includes("admin") && user?.role === "ADMIN") {
        return true;
      } else if (state.url.includes("docente") && user?.role === "TEACHER") {
        return true;
      } else if (state.url.includes("estudante") && user?.role === "STUDENT") {
        return true;
      }

      return false;
  }

}
