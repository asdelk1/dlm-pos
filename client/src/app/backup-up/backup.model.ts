import {User} from '../user-profile/user.model.';

export interface BackupLogEntry {
    id: number;
    time: string;
    user: User;
    fileName: string;
}
