CREATE OR REPLACE VIEW course_attendances AS
SELECT
    l.course_id,          
    l.id AS lesson_id,
    l.start_time,
    l.end_time,
    COALESCE(r.status, 0) as rollcall_status,
    u.id AS student_id, 
    u."name" AS student_name,
    u.school AS student_school,
    u.department AS student_department,
    u."no" AS student_no,
    COALESCE(a.status, 0) AS attendance_status
FROM lessons l
LEFT JOIN rollcalls r ON l.id = r.lesson_id
JOIN enrollments e ON l.course_id = e.course_id 
LEFT JOIN users u ON u.id = e.student_id
LEFT JOIN attendances a ON (e.student_id = a.student_id AND l.id = a.lesson_id);