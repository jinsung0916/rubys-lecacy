insert into mb (mb_no,
                nickname,
                account_locked_yn,
                row_stat_cd)
values (1, 'duplicatedNickname', 'N', 'C');

insert into mb (mb_no,
                quit_date,
                row_stat_cd)
values (2, '9999-12-31', 'D');

insert into mb_auth(mb_no,
                    mb_auth_ord,
                    mb_auth_cd,
                    identifier)
values (1, 1, 'APPLE', '12345');

insert into mb_auth(mb_no,
                    mb_auth_ord,
                    mb_auth_cd,
                    identifier)
values (2, 1, 'GOOGLE', '12345');

insert into mb_auth(mb_no,
                    mb_auth_ord,
                    mb_auth_cd,
                    identifier)
values (2, 2, 'GOOGLE', '12345');

insert into login_history(login_history_no,
                          mb_no,
                          login_chnl_cd,
                          login_history_cd,
                          row_stat_cd)
values (1, 1, 'APP', 'SUCCESS', 'C')