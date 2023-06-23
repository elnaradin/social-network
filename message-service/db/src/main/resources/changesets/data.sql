BEGIN;
SET CONSTRAINTS ALL DEFERRED;
insert into dialogs (is_deleted, last_message_id) values (false, 4);
insert into dialogs (is_deleted, last_message_id) values (false, 14);
insert into dialogs (is_deleted, last_message_id) values (false, 24);
insert into dialogs (is_deleted, last_message_id) values (false, 34);

insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-05-30T23:03:45Z', 1, 2, '11-050 - Library Equipment', 'READ', 1);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2022-06-01T23:05:45Z', 1, 2, '2-782 - Brick Pavers', 'READ', 1);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-05-30T23:07:45Z', 1, 2, '1-515 - Temporary Lighting', 'SENT', 1);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-06-27T23:09:45Z', 1, 2, '15-100 - Plumbing', 'SENT', 1);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-05-30T23:11:45Z', 1, 2, '10-300 - Fireplaces and Stoves', 'READ', 1);

insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2022-05-30T23:04:45Z', 2, 1, '4 - Masonry', 'READ', 1);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2022-05-30T23:06:45Z', 2, 1, '11-070 - Instrumental Equipment', 'SENT', 1);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-05-30T23:08:45Z', 2, 1, '2-625 - Retaining Wall Drainage Piping', 'READ', 1);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-05-30T23:10:45Z', 2, 1, '10-900 - Wardrobe and Closet Specialties', 'READ', 1);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2022-05-30T23:12:45Z', 2, 1, '1-002 - Instructions', 'SENT', 1);

insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2006-04-09T10:01:39Z', 1, 4, '1-002 - Instructions', 'SENT', 2);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2006-04-09T10:03:39Z', 1, 4, '15-500 - Heat-Generation Equipment', 'SENT', 2);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2006-05-01T10:05:39Z', 1, 4, '10-500 - Lockers', 'SENT', 2);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2006-05-09T10:07:39Z', 1, 4, '1-530 - Temporary Construction', 'READ', 2);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2006-04-09T10:09:39Z', 1, 4, '13-230 - Digester Covers and Appurtenances', 'SENT', 2);

insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2006-04-09T10:02:39Z', 4, 1, '7-900 - Joint Sealers', 'READ', 2);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2006-04-09T10:04:39Z', 4, 1, '2-362 - Termite Control', 'READ', 2);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2006-04-09T10:06:39Z', 4, 1, '10-450 - Pedestrian Control Devices', 'SENT', 2);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2006-04-09T10:08:39Z', 4, 1, '2-230 - Site Clearing', 'READ', 2);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2006-04-09T10:09:39Z', 4, 1, '7-100 - Damproofing and Waterproofing', 'READ', 2);

insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2022-05-07T13:02:58Z', 2, 3, '1-520 - Construction Facilities', 'READ', 3);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2022-05-07T13:04:58Z', 2, 3, '3-300 - Footings', 'SENT', 3);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-06-01T13:06:58Z', 2, 3, '10-240 - Grilles and Screens', 'SENT', 3);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2022-06-07T13:08:58Z', 2, 3, '11-700 - Medical Equipment', 'SENT', 3);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2022-05-07T13:10:58Z', 2, 3, '2-310 - Grading', 'SENT', 3);

insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-05-07T13:03:58Z', 3, 2, '7-100 - Damproofing and Waterproofing', 'READ', 3);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-05-07T13:05:58Z', 3, 2, '7-600 - Flashing and Sheet Metal', 'READ', 3);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-05-07T13:07:58Z', 3, 2, '9-400 - Terrazzo', 'SENT', 3);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2022-05-07T13:09:58Z', 3, 2, '3-100 - Concrete Reinforcement', 'SENT', 3);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-05-07T13:11:58Z', 3, 2, '7-100 - Damproofing and Waterproofing', 'SENT', 3);

insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2022-05-28T01:38:45Z', 3, 4, '15-950 - Testing, Adjusting, and Balancing', 'SENT', 4);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2022-05-28T03:38:45Z', 3, 4, '3-310 - Expansion Joints', 'SENT', 4);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2022-06-01T05:38:45Z', 3, 4, '2-813 - Lawn Sprinkling and Irrigation', 'SENT', 4);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2022-06-28T07:38:45Z', 3, 4, '13-030 - Special Purpose Rooms', 'SENT', 4);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-05-28T09:38:45Z', 3, 4, '11-600 - Laboratory Equipment', 'SENT', 4);

insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-05-28T02:38:45Z', 4, 3, '13-160 - Aquariums', 'SENT', 4);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (false, '2022-05-28T04:38:45Z', 4, 3, '9-700 - Wall Finishes', 'READ', 4);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-05-28T06:38:45Z', 4, 3, '17 - Markup and Contingency', 'SENT', 4);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-05-28T08:38:45Z', 4, 3, '2-740 - Flexible Pavement Asphalt Pavement', 'READ', 4);
insert into messages (is_deleted, time, author_id, recipient_id, message_text, status, dialog_id) values (true, '2022-05-28T10:38:45Z', 4, 3, '3-000 - General', 'SENT', 4);

insert into account_dialogs (dialog_id, account_id, recipient_id) values (1, 1, 2);
insert into account_dialogs (dialog_id, account_id, recipient_id) values (1, 2, 1);
insert into account_dialogs (dialog_id, account_id, recipient_id) values (2, 1, 4);
insert into account_dialogs (dialog_id, account_id, recipient_id) values (2, 4, 1);
insert into account_dialogs (dialog_id, account_id, recipient_id) values (3, 2, 3);
insert into account_dialogs (dialog_id, account_id, recipient_id) values (3, 3, 2);
insert into account_dialogs (dialog_id, account_id, recipient_id) values (4, 4, 3);
insert into account_dialogs (dialog_id, account_id, recipient_id) values (4, 3, 4);
COMMIT;
