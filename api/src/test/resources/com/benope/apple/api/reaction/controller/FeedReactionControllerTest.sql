insert into mb (mb_no,
                nickname,
                row_stat_cd)
values (1, 'AJ', 'C');

insert into mb (mb_no,
                row_stat_cd)
values (2, 'C');

INSERT INTO feed(feed_no,
                 mb_no,
                 feed_type_cd,
                 view_cnt,
                 comment_cnt,
                 like_cnt,
                 row_stat_cd)
VALUES (1, 2, 'FEED', 0, 0, 1, 'C');

insert into reaction(mb_no,
                     object_no,
                     reaction_type_cd,
                     reaction_target_cd,
                     row_stat_cd)
values (2, 1, 'LIKE', 'FEED', 'C');

insert into mb_alarm_agree(mb_no,
                           mb_alarm_agree_ord,
                           alarm_agree_cd,
                           alarm_agree_yn)
values (1, 1, 'ALARM01', 'Y');

insert into mb_alarm_agree(mb_no,
                           mb_alarm_agree_ord,
                           alarm_agree_cd,
                           alarm_agree_yn)
values (2, 1, 'ALARM01', 'Y');