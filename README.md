# RSS Hot Topics

Implementation of a hot topic analysis for RSS feeds.

## APIs

### /analyse/new

Take at least two RSS URLs as a parameter (more are possible)

        ["https://news.google.com/news?cf=all&hl=en&pz=1&ned=us&output=rss",
        "https://rss.nytimes.com/services/xml/rss/nyt/World.xml",
        "http://feeds.bbci.co.uk/news/world/rss.xml"]

**Response** an unique identifier, which will be the input for the second API endpoint.

        8b5bd8ac-0860-4a4a-beec-124294d917e2

### /frequency/{id}

Take an id as input

        8b5bd8ac-0860-4a4a-beec-124294d917e2

**Output**
1. The three elements with the most matches.
2. The orignal news header.
3. The link to the whole news text should be displayed.

## Sample RSS Feed

        //Sample Google RSS Feed
        Putin dismisses criticism of hacking and internal crackdowns ahead of Biden summit - NBC News
        Joe Biden was corrected by Boris Johnson after the president interrupted him at the G7 summit - Business Insider
        China pushes back against G-7 joint statement, blames 'sinister intentions' of US, others - Fox News
        Boris Johnson set to postpone England’s ‘freedom day’ as delta variant cases climb - The Washington Post
        Delta COVID-19 variant 'probably going to become' dominant strain in US, Gottlieb says - Fox News

        //Sample Nytimes RSS Feed
        As Biden Meeting Nears, Erdogan Softens His Stance
        President Biden Will Meet Queen Elizabeth at Windsor Castle
        To Counter China's Belt-and-Road, Biden Tries to Unite G7
        Biden Will Hold Separate News Conference From Putin  After Summit
        French Companies Admit Problems at Nuclear Plant in China
        Explosion at Produce Market Kills at Least 12 in China
        Delta Variant Producing More Severe Illness, Doctors in China Say

## Run

        mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080

