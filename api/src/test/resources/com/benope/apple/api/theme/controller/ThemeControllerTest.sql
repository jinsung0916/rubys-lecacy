INSERT INTO theme (theme_no, theme_nm, display_nm, reorder_no, row_stat_cd)
VALUES (1, '테마', '전시명', 5, 'C');

INSERT INTO theme (theme_no, theme_nm, display_nm, reorder_no, row_stat_cd)
VALUES (2, '테마', '전시명', 4, 'C');

INSERT INTO theme (theme_no, theme_nm, display_nm, reorder_no, row_stat_cd)
VALUES (3, '테마', '전시명', 3, 'C');

INSERT INTO theme (theme_no, theme_nm, display_nm, reorder_no, row_stat_cd)
VALUES (4, '테마', '전시명', 2, 'C');

INSERT INTO theme (theme_no, theme_nm, display_nm, reorder_no, picked, row_stat_cd)
VALUES (5, '테마', '전시명', 1, true, 'C');

INSERT INTO theme_feed(theme_feed_no, theme_no, feed_no, reorder_no, row_stat_cd)
VALUES (1, 5, 1, 1, 'C');

INSERT INTO theme_feed(theme_feed_no, theme_no, feed_no, reorder_no, row_stat_cd)
VALUES (2, 5, 2, 1, 'C');

INSERT INTO theme_feed(theme_feed_no, theme_no, feed_no, reorder_no, row_stat_cd)
VALUES (3, 5, 3, 1, 'C');

INSERT INTO theme_feed(theme_feed_no, theme_no, feed_no, reorder_no, row_stat_cd)
VALUES (4, 5, 4, 1, 'C');

INSERT INTO theme_feed(theme_feed_no, theme_no, feed_no, reorder_no, row_stat_cd)
VALUES (5, 5, 5, 1, 'C');

INSERT INTO feed(feed_no, row_stat_cd)
VALUES (1, 'C');

INSERT INTO feed(feed_no, row_stat_cd)
VALUES (2, 'C');

INSERT INTO feed(feed_no, row_stat_cd)
VALUES (3, 'C');

INSERT INTO feed(feed_no, row_stat_cd)
VALUES (4, 'C');

INSERT INTO feed(feed_no, row_stat_cd)
VALUES (5, 'C');