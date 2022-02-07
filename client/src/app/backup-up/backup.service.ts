import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {BackupLogEntry} from './backup.model';
import {HttpClient, HttpEvent} from '@angular/common/http';
import {ApiService} from '../api/api.service';

@Injectable({
    providedIn: 'root'
})
export class BackupService {

    constructor(private http: HttpClient,
                private apiService: ApiService) {
    }

    public fetchLog(): Observable<BackupLogEntry[]> {
        const url: string = `${this.apiService.getBaseURL()}/database/backups`;
        return this.http.get<BackupLogEntry[]>(url);
    }

    // public createBackup(): Observable<HttpEvent<Blob>> {
    //     const url: string = `${this.apiService.getBaseURL()}/database/backups`;
    //     const options: any = {
    //         responseType: 'blob'
    //     };
    //     return this.http.post<Blob>(url, {}, options);
    // }

    public createBackup(): Observable<any> {
        const url: string = `${this.apiService.getBaseURL()}/database/backups`;
        const options: any = {
            responseType: 'blob'
        };
        return this.http.post<any>(url, {});
    }
}
