insert into mb (mb_no,
                row_stat_cd)
values (1, 'C');

insert into mb (mb_no,
                row_stat_cd)
values (2, 'C');

INSERT INTO feed(feed_no,
                 mb_no,
                 feed_type_cd,
                 rpst_img_no,
                 scrap_cnt,
                 hide_yn,
                 created_by,
                 row_stat_cd)
VALUES (1, 1, 'FEED', 1, 1, 'N', 1, 'C');

INSERT INTO feed(feed_no,
                 mb_no,
                 feed_type_cd,
                 rpst_img_no,
                 scrap_cnt,
                 hide_yn,
                 created_by,
                 row_stat_cd)
VALUES (2, 1, 'FEED', 1, 1, 'N', 1, 'C');

INSERT INTO feed(feed_no,
                 mb_no,
                 feed_type_cd,
                 rpst_img_no,
                 scrap_cnt,
                 hide_yn,
                 created_by,
                 row_stat_cd)
VALUES (3, 1, 'FEED', 1, 1, 'N', 1, 'C');

insert into scrap (scrap_no,
                   scrap_type_cd,
                   parent_directory_no,
                   mb_no,
                   feed_no,
                   row_stat_cd)
values (1, 'SCRAP', 0, 1, 1, 'C');

insert into scrap (scrap_no,
                   scrap_type_cd,
                   parent_directory_no,
                   mb_no,
                   directory_name,
                   row_stat_cd)
values (2, 'DIRECTORY', 0, 1, 'directory', 'C');

insert into scrap (scrap_no,
                   scrap_type_cd,
                   parent_directory_no,
                   mb_no,
                   feed_no,
                   row_stat_cd)
values (3, 'SCRAP', 2, 1, 2, 'C');

insert into scrap (scrap_no,
                   scrap_type_cd,
                   parent_directory_no,
                   mb_no,
                   feed_no,
                   row_stat_cd)
values (4, 'SCRAP', 0, 1, 3, 'C');