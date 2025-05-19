DO $$
    DECLARE
        bikes_category_id UUID;
        bmx_sub_category_id UUID;
        mountain_sub_category_id UUID;
        racing_sub_category_id UUID;
        exists_table bool;
    BEGIN

        SELECT EXISTS (
           SELECT FROM information_schema.tables
           WHERE  table_schema = 'public'
           AND    table_name   = 'category'
        ) into exists_table;

        IF exists_table IS TRUE THEN

            CREATE TABLE IF NOT EXISTS sub_category(
                id uuid primary key,
                name varchar not null,
                description varchar,
                category_id uuid references category(id)
            );
            CREATE TABLE IF NOT EXISTS product(
                id uuid primary key,
                name varchar not null,
                description text not null,
                sub_category_id uuid references sub_category(id),
                photo_url varchar
            );

            SELECT id INTO bikes_category_id
            FROM category
            WHERE name = 'Bikes';

            INSERT INTO sub_category (id, name, description, category_id)
            VALUES (gen_random_uuid(), 'BMX', 'Mainly bikes for skateparks and flat roads', bikes_category_id)
            RETURNING id INTO bmx_sub_category_id;

            INSERT INTO sub_category (id, name, description, category_id)
            VALUES (gen_random_uuid(), 'Mountain Bike', '4x4 bikes that can be used everywhere', bikes_category_id)
            RETURNING id INTO mountain_sub_category_id;

            INSERT INTO sub_category (id, name, description, category_id)
            VALUES (gen_random_uuid(), 'Racing', 'Competitive bikes or for Sunday enjoyers', bikes_category_id)
            RETURNING id INTO racing_sub_category_id;

            INSERT INTO product (id, name, description, sub_category_id, photo_url) VALUES
                (gen_random_uuid(), 'Carbon Fiber Saddle', 'Lightweight carbon fiber saddle with ergonomic design for road cycling.', racing_sub_category_id, NULL),
                (gen_random_uuid(), 'Clipless Pedals', 'Durable clipless pedals compatible with SPD cleats for efficient power transfer.', mountain_sub_category_id, NULL),
                (gen_random_uuid(), '26-inch MTB Tire', 'All-terrain 26-inch mountain bike tire with aggressive tread pattern.', mountain_sub_category_id, NULL),
                (gen_random_uuid(), 'Drop Handlebars', 'Aluminum drop handlebars with 42cm width for road bikes.', racing_sub_category_id, NULL),
                (gen_random_uuid(), 'Hydraulic Disc Brake Set', 'High-performance hydraulic disc brake set with 160mm rotors.', bmx_sub_category_id, NULL),
                (gen_random_uuid(), '9-Speed Cassette', '9-speed cassette with 11-32T range for smooth shifting.', mountain_sub_category_id, NULL),
                (gen_random_uuid(), 'Gel Bike Seat Cover', 'Comfortable gel seat cover for long rides, fits most saddles.', racing_sub_category_id, NULL),
                (gen_random_uuid(), 'Chainring 50T', '50-tooth chainring for road bikes, compatible with 130mm BCD cranks.', bmx_sub_category_id, NULL),
                (gen_random_uuid(), 'MTB Flat Pedals', 'Wide platform flat pedals with sealed bearings for mountain biking.', mountain_sub_category_id, NULL),
                (gen_random_uuid(), '700c Road Tire', 'Puncture-resistant 700c road tire with 28mm width.', racing_sub_category_id, NULL);
        END IF;
    END $$;

COMMIT;