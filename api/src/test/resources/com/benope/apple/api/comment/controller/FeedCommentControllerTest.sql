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
                 comment_cnt,
                 row_stat_cd)
VALUES (1, 2, 'FEED', 1, 'C');

INSERT INTO comment(comment_no,
                    comment_type_cd,
                    mb_no,
                    object_no,
                    sub_comment_cnt,
                    created_by,
                    row_stat_cd)
VALUES (1, 'FEED', 2, 1, 1, 2, 'C');

INSERT INTO comment_relation(parent_comment_no,
                             child_comment_no,
                             reorder_no,
                             depth)
VALUES (0, 1, 0, 0);

INSERT INTO comment(comment_no,
                    comment_type_cd,
                    mb_no,
                    object_no,
                    sub_comment_cnt,
                    created_by,
                    row_stat_cd)
VALUES (2, 'FEED', 2, 1, 0, 2, 'C');

INSERT INTO comment_relation(parent_comment_no,
                             child_comment_no,
                             reorder_no,
                             depth)
VALUES (1, 2, 0, 1);

insert into mb_alarm_agree(mb_no,
                           mb_alarm_agree_ord,
                           alarm_agree_cd,
                           alarm_agree_yn)
values (1, 1, 'ALARM02', 'Y');

insert into mb_alarm_agree(mb_no,
                           mb_alarm_agree_ord,
                           alarm_agree_cd,
                           alarm_agree_yn)
values (1, 2, 'ALARM03', 'Y');

insert into mb_alarm_agree(mb_no,
                           mb_alarm_agree_ord,
                           alarm_agree_cd,
                           alarm_agree_yn)
values (2, 1, 'ALARM02', 'Y');

insert into mb_alarm_agree(mb_no,
                           mb_alarm_agree_ord,
                           alarm_agree_cd,
                           alarm_agree_yn)
values (2, 2, 'ALARM03', 'Y');