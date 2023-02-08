insert into mb (mb_no,
                nickname,
                row_stat_cd)
values (1, 'AJ', 'C');

insert into mb (mb_no,
                row_stat_cd)
values (2, 'C');

insert into mb_alarm_agree (mb_no,
                            mb_alarm_agree_ord,
                            alarm_agree_cd,
                            alarm_agree_yn,
                            alarm_agree_dt)
values (1, 1, 'ALARM01', 'Y', '20220515');

insert into mb_alarm_agree (mb_no,
                            mb_alarm_agree_ord,
                            alarm_agree_cd,
                            alarm_agree_yn,
                            alarm_agree_dt)
values (1, 2, 'ALARM02', 'Y', '20220515');

insert into mb_alarm_agree (mb_no,
                            mb_alarm_agree_ord,
                            alarm_agree_cd,
                            alarm_agree_yn,
                            alarm_agree_dt)
values (1, 3, 'ALARM03', 'Y', '20220515');

insert into mb_alarm_agree (mb_no,
                            mb_alarm_agree_ord,
                            alarm_agree_cd,
                            alarm_agree_yn,
                            alarm_agree_dt)
values (1, 4, 'ALARM04', 'Y', '20220515');
