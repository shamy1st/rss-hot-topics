# RSS Hot Topics

Implementation of a hot topic analysis for RSS feeds.

## APIs

### /analyse/new

Take at least two RSS URLs as a parameter (more are possible)

        ["https://news.google.com/news?cf=all&hl=en&pz=1&ned=us&output=rss",
        "https://rss.nytimes.com/services/xml/rss/nyt/World.xml",
        "https://rss.msn.com/en-us",
        "http://feeds.bbci.co.uk/news/world/rss.xml",
        "http://feeds.washingtonpost.com/rss/world"]

**Response** an unique identifier, which will be the input for the second API endpoint.

        8b5bd8ac-0860-4a4a-beec-124294d917e2

### /frequency/{id}

Take an id as input

        8b5bd8ac-0860-4a4a-beec-124294d917e2

**Output**
1. The three elements with the most matches.
2. The orignal news header.
3. The link to the whole news text should be displayed.

