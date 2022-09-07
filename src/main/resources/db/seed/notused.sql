insert
into prescription
(created_at, updated_at, created_by, updated_by, administer_interval, daily_count, end_date, medicine_count, member_id,
 prescribe_cnt, total_day_count, treat_date, treat_dsnm, treat_medicalnm, treat_type, treat_dsgb, visit_count,
 prescription_id)
values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);

insert
into medicine
(created_at, updated_at, created_by, updated_by, detail_administer_count, detail_medicine_effect, detail_medicine_name,
 detail_prescribe_count, prescription_id, detail_treat_date, detail_treat_type, medicine_id)
values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);

insert
into member
(member_id, callback_data, callback_type, callback_id, carrier, email, jumin, member_role, my_data_detail_update_time,
 my_data_update_time, name, nick_name, phone_number, profile_image_url)
values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);