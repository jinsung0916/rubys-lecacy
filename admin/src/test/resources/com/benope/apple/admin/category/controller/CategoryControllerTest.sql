INSERT INTO category (category_no,
                      category_type_cd,
                      parent_category_no,
                      category_nm,
                      row_stat_cd)
VALUES (1, 'FOOD', 0, '부모1', 'C');

INSERT INTO category (category_no,
                      category_type_cd,
                      parent_category_no,
                      category_nm,
                      row_stat_cd)
VALUES (2, 'FOOD', 0, '부모2', 'C');

INSERT INTO category (category_no,
                      category_type_cd,
                      parent_category_no,
                      category_nm,
                      row_stat_cd)
VALUES (3, 'FOOD', 0, '부모3', 'D');

INSERT INTO category (category_no,
                      category_type_cd,
                      parent_category_no,
                      category_nm,
                      row_stat_cd)
VALUES (4, 'FOOD', 1, '자식1', 'C');

INSERT INTO category (category_no,
                      category_type_cd,
                      parent_category_no,
                      category_nm,
                      row_stat_cd)
VALUES (5, 'RANKING', 0, '랭킹1', 'C');

INSERT INTO food(food_no,
                 food_category_no,
                 row_stat_cd)
values (1, 4, 'C');