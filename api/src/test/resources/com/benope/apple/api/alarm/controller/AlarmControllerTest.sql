insert into mb(mb_no,
               nickname,
               row_stat_cd)
values (1, 'AJ', 'C');

insert into mb(mb_no,
               nickname,
               row_stat_cd)
values (2, 'hong', 'C');

insert into mb(mb_no,
               nickname,
               row_stat_cd)
values (3, 'brain', 'D');

insert into alarm(alarm_no,
                  alarm_type_cd,
                  alarm_target_cd,
                  from_mb_no,
                  to_mb_no,
                  object_no,
                  content,
                  read_yn,
                  row_stat_cd)
values (1, 'LIKE', 'FEED', 1, 1, 1, '%s님이 회원님의 게시글을 좋아합니다.', 'N', 'C');

insert into alarm(alarm_no,
                  alarm_type_cd,
                  alarm_target_cd,
                  from_mb_no,
                  to_mb_no,
                  object_no,
                  content,
                  read_yn,
                  row_stat_cd)
values (2, 'LIKE', 'FEED', 1, 1, 2, '%s님이 회원님의 게시글을 좋아합니다.', 'N', 'C');

insert into alarm(alarm_no,
                  alarm_type_cd,
                  alarm_target_cd,
                  from_mb_no,
                  to_mb_no,
                  object_no,
                  content,
                  read_yn,
                  row_stat_cd)
values (3, 'COMMENT', 'FEED', 1, 1, 1, '%s님이 회원님의 게시글을 좋아합니다.', 'N', 'C');

insert into alarm(alarm_no,
                  alarm_type_cd,
                  alarm_target_cd,
                  from_mb_no,
                  to_mb_no,
                  object_no,
                  content,
                  read_yn,
                  row_stat_cd)
values (4, 'COMMENT', 'FEED', 2, 1, 2, '%s님이 회원님의 게시글을 좋아합니다.', 'N', 'C');

insert into alarm(alarm_no,
                  alarm_type_cd,
                  alarm_target_cd,
                  from_mb_no,
                  to_mb_no,
                  object_no,
                  content,
                  read_yn,
                  row_stat_cd)
values (5, 'COMMENT', 'COMMUNITY', 2, 1, 1, '%s님이 회원님의 게시글을 좋아합니다.', 'N', 'C');

insert into alarm(alarm_no,
                  alarm_type_cd,
                  alarm_target_cd,
                  from_mb_no,
                  to_mb_no,
                  object_no,
                  content,
                  read_yn,
                  row_stat_cd)
values (6, 'SUB_COMMENT', 'FEED', 2, 1, 1, '%s님이 회원님의 게시글을 좋아합니다.', 'Y', 'C');

insert into alarm(alarm_no,
                  alarm_type_cd,
                  alarm_target_cd,
                  from_mb_no,
                  to_mb_no,
                  object_no,
                  content,
                  read_yn,
                  row_stat_cd)
values (7, 'SUB_COMMENT', 'FEED', 3, 1, 1, '<b>%s</b>님이 회원님의 게시글을 좋아합니다.', 'Y', 'C');

insert into alarm(alarm_no,
                  alarm_type_cd,
                  alarm_target_cd,
                  from_mb_no,
                  to_mb_no,
                  object_no,
                  content,
                  read_yn,
                  row_stat_cd)
values (8, 'SUB_COMMENT', 'COMMUNITY', 3, 1, 1, '%s님이 회원님의 게시글을 좋아합니다.', 'Y', 'C');

insert into alarm(alarm_no,
                  alarm_type_cd,
                  alarm_target_cd,
                  from_mb_no,
                  to_mb_no,
                  object_no,
                  content,
                  read_yn,
                  row_stat_cd)
values (9, 'FOLLOW', 'MEMBER', 3, 1, 1, '%s님이 회원님의 게시글을 좋아합니다.', 'Y', 'C');

insert into feed(feed_no,
                 feed_type_cd,
                 mb_no,
                 row_stat_cd)
values (1, 'FEED', 1, 'C');

insert into feed(feed_no,
                 feed_type_cd,
                 mb_no,
                 row_stat_cd)
values (2, 'RECIPE', 1, 'D');