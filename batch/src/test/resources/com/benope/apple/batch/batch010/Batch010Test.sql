insert into food(food_no,
                 food_nm,
                 front_image_no,
                 row_stat_cd)
values (1, '다', 1, 'C');

insert into food(food_no,
                 food_nm,
                 front_image_no,
                 row_stat_cd)
values (2, '나', 2, 'C');

insert into food(food_no,
                 food_nm,
                 front_image_no,
                 row_stat_cd)
values (3, '가', 3, 'C');


insert into food(food_no,
                 front_image_no,
                 row_stat_cd)
values (4, 4, 'C');

insert into food(food_no,
                 row_stat_cd)
values (5, 'C');

insert into ranking(ranking_type_cd,
                    object_no,
                    rank_date,
                    row_stat_cd)
values ('FOOD_REALTIME', 4, '2023-01-02', 'C');