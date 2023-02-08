insert into mb (mb_no,
                nickname,
                email,
                profile_image_no,
                account_locked_yn,
                expert_cd,
                row_stat_cd)
values (1, 'example', 'example@example.com', 1, 'N', 'PRO', 'C');


insert into mb (mb_no,
                row_stat_cd)
values (2, 'C');

insert into follow(follow_no,
                   mb_no,
                   follow_mb_no,
                   follow_date,
                   row_stat_cd)
values (1, 2, 1, '9999-12-31', 'C');

insert into follow(follow_no,
                   mb_no,
                   follow_mb_no,
                   follow_date,
                   row_stat_cd)
values (2, 1, 2, '9999-12-31', 'C');