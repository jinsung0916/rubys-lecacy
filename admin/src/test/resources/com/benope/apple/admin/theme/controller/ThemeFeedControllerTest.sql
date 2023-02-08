INSERT INTO theme (theme_no,
                   theme_nm,
                   display_nm,
                   reorder_no,
                   row_stat_cd)
VALUES (1, '테마', '전시명', 1, 'C');

INSERT INTO theme_feed (theme_feed_no,
                        theme_no,
                        feed_no,
                        reorder_no,
                        row_stat_cd)
values (1, 1, 1, 1, 'C');

INSERT INTO theme_feed (theme_feed_no,
                        theme_no,
                        feed_no,
                        reorder_no,
                        row_stat_cd)
values (2, 1, 2, 2, 'C');

INSERT INTO theme_feed (theme_feed_no,
                        theme_no,
                        feed_no,
                        reorder_no,
                        row_stat_cd)
values (3, 1, 3, 2, 'C');

INSERT INTO theme_feed (theme_feed_no,
                        theme_no,
                        feed_no,
                        reorder_no,
                        row_stat_cd)
values (4, 1, 4, 3, 'C');

INSERT INTO theme_feed (theme_feed_no,
                        theme_no,
                        feed_no,
                        reorder_no,
                        row_stat_cd)
values (5, 1, 5, 4, 'C');

INSERT INTO feed (feed_no,
                  mb_no,
                  feed_type_cd,
                  row_stat_cd)
VALUES (1, 1, 'FEED', 'C');

INSERT INTO feed (feed_no,
                  mb_no,
                  feed_type_cd,
                  row_stat_cd)
VALUES (2, 1, 'FEED', 'C');

INSERT INTO feed (feed_no,
                  mb_no,
                  feed_type_cd,
                  row_stat_cd)
VALUES (3, 1, 'FEED', 'C');

INSERT INTO feed (feed_no,
                  mb_no,
                  feed_type_cd,
                  row_stat_cd)
VALUES (4, 1, 'FEED', 'D');

INSERT INTO feed (feed_no,
                  mb_no,
                  feed_type_cd,
                  row_stat_cd)
VALUES (6, 1, 'FEED', 'C');