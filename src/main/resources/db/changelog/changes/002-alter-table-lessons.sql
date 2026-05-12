alter table lesson add rollcall_start_time timestamptz;
alter table lesson add rollcall_end_time timestamptz;
alter table lesson add rollcall_rotation_time int;
alter table lesson add rollcall_mode int;
alter table lesson rename attendance_status to rollcall_status;
alter table lesson rename attendance_code to rollcall_code;
