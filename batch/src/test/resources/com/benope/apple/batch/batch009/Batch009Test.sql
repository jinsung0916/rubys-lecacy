INSERT INTO theme(theme_no,
                  theme_conditions,
                  row_stat_cd)
VALUES (1, '[
          {
            "theme_condition_cd": "EXPERT",
            "expert_cd": "PRO",
            "batch_size": 2
          }
        ]' FORMAT JSON, 'C');

INSERT INTO feed(feed_no,
                 mb_no,
                 row_stat_cd)
VALUES (1, 1, 'C');

INSERT INTO feed(feed_no,
                 mb_no,
                 row_stat_cd)
VALUES (2, 1, 'C');

INSERT INTO feed(feed_no,
                 mb_no,
                 row_stat_cd)
VALUES (3, 1, 'C');

INSERT INTO mb(mb_no,
               expert_cd,
               row_stat_cd)
VALUES (1, 'PRO', 'C');