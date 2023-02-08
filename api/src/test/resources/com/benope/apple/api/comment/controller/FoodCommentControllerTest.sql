insert into mb (mb_no,
                nickname,
                row_stat_cd)
values (1, 'AJ', 'C');

insert into mb (mb_no,
                nickname,
                row_stat_cd)
values (2, 'Hong', 'C');


INSERT INTO food(food_no,
                 row_stat_cd)
VALUES (1, 'C');

INSERT INTO food(food_no,
                 row_stat_cd)
VALUES (2, 'C');

INSERT INTO comment(comment_no,
                    comment_type_cd,
                    mb_no,
                    object_no,
                    sub_comment_cnt,
                    hide_yn,
                    created_by,
                    row_stat_cd)
VALUES (1, 'FOOD', 1, 1, 0, 'N', 1, 'C');

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
                    hide_yn,
                    created_by,
                    row_stat_cd)
VALUES (2, 'FOOD', 2, 1, 0, 'N', 2, 'C');

INSERT INTO comment_relation(parent_comment_no,
                             child_comment_no,
                             reorder_no,
                             depth)
VALUES (0, 2, 0, 0);

INSERT INTO comment(comment_no,
                    comment_type_cd,
                    mb_no,
                    object_no,
                    sub_comment_cnt,
                    hide_yn,
                    created_by,
                    row_stat_cd)
VALUES (3, 'FOOD', 1, 2, 0, 'N', 1, 'C');

INSERT INTO comment_relation(parent_comment_no,
                             child_comment_no,
                             reorder_no,
                             depth)
VALUES (0, 3, 0, 0);

INSERT INTO score(score_no,
                  mb_no,
                  object_no,
                  score,
                  row_stat_cd)
values (1, 1, 1, 5.0, 'C');