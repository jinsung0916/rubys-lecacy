insert into mb (mb_no,
                nickname,
                row_stat_cd)
values (1, 'AJ', 'C');

insert into mb (mb_no,
                row_stat_cd)
values (2, 'D');

insert into mb (mb_no,
                row_stat_cd)
values (10, 'C');

insert into follow (follow_no,
                    mb_no,
                    follow_mb_no,
                    follow_date,
                    created_by,
                    row_stat_cd)
values (1, 10, 1, '99991231', 10, 'C');

insert into follow (follow_no,
                    mb_no,
                    follow_mb_no,
                    follow_date,
                    created_by,
                    row_stat_cd)
values (2, 2, 10, '99991231', 2, 'C');

insert into follow (follow_no,
                    mb_no,
                    follow_mb_no,
                    follow_date,
                    created_by,
                    row_stat_cd)
values (3, 1, 2, '99991231', 1, 'C');

insert into mb_alarm_agree(mb_no,
                           mb_alarm_agree_ord,
                           alarm_agree_cd,
                           alarm_agree_yn)
values (1, 1, 'ALARM04', 'Y');

insert into mb_alarm_agree(mb_no,
                           mb_alarm_agree_ord,
                           alarm_agree_cd,
                           alarm_agree_yn)
values (10, 1, 'ALARM04', 'Y');