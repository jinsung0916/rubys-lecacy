insert into mb(mb_no,
               nickname,
               row_stat_cd)
values (1, 'AJ', 'C');

insert into mb(mb_no,
               row_stat_cd)
values (2, 'C');

insert into mb(mb_no,
               row_stat_cd)
values (3, 'C');

insert into mb(mb_no,
               row_stat_cd)
values (4, 'C');

insert into mb_auth(mb_no,
                    mb_auth_ord,
                    mb_auth_cd,
                    identifier)
values (1, 0, 'GOOGLE', 'a');

insert into mb_auth(mb_no,
                    mb_auth_ord,
                    mb_auth_cd,
                    identifier)
values (2, 0, 'APPLE', 'b');

insert into mb_auth(mb_no,
                    mb_auth_ord,
                    mb_auth_cd,
                    identifier)
values (3, 0, 'NAVER', 'c');

insert into mb_auth(mb_no,
                    mb_auth_ord,
                    mb_auth_cd,
                    identifier)
values (4, 0, 'KAKAO', 'd');

insert into login_history(login_history_no,
                          mb_no,
                          last_access_at,
                          row_stat_cd)
values (1, 1, '2023-01-17 17:05:00', 'C');