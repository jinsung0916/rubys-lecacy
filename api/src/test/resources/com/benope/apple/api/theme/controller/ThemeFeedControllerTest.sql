INSERT INTO theme (theme_no,
                   row_stat_cd)
VALUES (1, 'C');

INSERT INTO theme (theme_no,
                   display_all,
                   row_stat_cd)
VALUES (2, true, 'C');

INSERT INTO theme_feed (theme_no,
                        feed_no,
                        reorder_no,
                        row_stat_cd)
values (1, 1, 1, 'C');

INSERT INTO theme_feed (theme_no,
                        feed_no,
                        reorder_no,
                        row_stat_cd)
values (1, 2, 2, 'C');

INSERT INTO theme_feed (theme_no,
                        feed_no,
                        reorder_no,
                        row_stat_cd)
values (1, 3, 2, 'C');

INSERT INTO theme_feed (theme_no,
                        feed_no,
                        reorder_no,
                        row_stat_cd)
values (1, 4, 3, 'C');

INSERT INTO theme_feed (theme_no,
                        feed_no,
                        reorder_no,
                        row_stat_cd)
values (1, 5, 4, 'C');

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