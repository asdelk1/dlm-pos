import {Component, OnInit} from '@angular/core';
import {BackupLogEntry} from './backup.model';
import {BackupService} from './backup.service';
import {NotificationsService, NotificationType} from '../notifications/notifications.service';
import {HttpEvent} from '@angular/common/http';
import * as fileSaver from 'file-saver';

@Component({
  selector: 'app-backup-up',
  templateUrl: './backup-up.component.html',
  styleUrls: ['./backup-up.component.css']
})
export class BackupUpComponent implements OnInit {

  public entries: BackupLogEntry[] = [];

  constructor(private backupService: BackupService,
              private notificationService: NotificationsService) { }

  ngOnInit(): void {
    this.loadEntries();
  }

  private loadEntries(): void {
    this.backupService.fetchLog().subscribe(
        (res: BackupLogEntry[] ) => {
          this.entries = res;
        }
    )
  }

  public createBackup(): void {
    this.backupService.createBackup().subscribe(
        (res: any) => {
          this.notificationService.showNotification('Database Backed Up Successfully', NotificationType.SUCCESS);
            // const blob = new Blob([res], { type: 'text/sql' });
            // const url= window.URL.createObjectURL(blob);
            // window.open(url);
            const blob: any = new Blob([res], {type: 'text/sql'});
            fileSaver.saveAs(blob, 'backup.sql');
        },
        (errorObj: any) => {
            this.notificationService.showNotification(errorObj.error, NotificationType.DANGER);
        }
    );
  }



}
