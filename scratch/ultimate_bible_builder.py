import sqlite3
import re
import hashlib
import os

def to_1611(text):
    if not text: return ""
    text = text.replace('j', 'i').replace('J', 'I')
    words = text.split()
    new_words = []
    for word in words:
        match = re.match(r'^(\W*)(.*?)(\W*)$', word)
        if not match:
            new_words.append(word)
            continue
        prefix, base, suffix = match.groups()
        if not base:
            new_words.append(word)
            continue
        first = base[0]
        rest = base[1:]
        if first.lower() == 'u':
            first = 'V' if first.isupper() else 'v'
        rest = rest.replace('v', 'u').replace('V', 'U')
        new_words.append(prefix + first + rest + suffix)
    return ' '.join(new_words)

KJV_TITLES = [
    ("Genesis", "The first Booke of Moses, called Genesis", "OLD_TESTAMENT", "The First Book of Moses: Called Genesis"),
    ("Exodus", "The second Booke of Moses, called Exodus", "OLD_TESTAMENT", "The Second Book of Moses: Called Exodus"),
    ("Leviticus", "The third Booke of Moses, called Leviticus", "OLD_TESTAMENT", "The Third Book of Moses: Called Leviticus"),
    ("Numbers", "The fourth Booke of Moses, called Numbers", "OLD_TESTAMENT", "The Fourth Book of Moses: Called Numbers"),
    ("Deuteronomy", "The fifth Booke of Moses, called Deuteronomie", "OLD_TESTAMENT", "The Fifth Book of Moses: Called Deuteronomy"),
    ("Joshua", "The Booke of Ioshua", "OLD_TESTAMENT", "The Book of Joshua"),
    ("Judges", "The Booke of Iudges", "OLD_TESTAMENT", "The Book of Judges"),
    ("Ruth", "The Booke of Ruth", "OLD_TESTAMENT", "The Book of Ruth"),
    ("1 Samuel", "The first Booke of Samuel", "OLD_TESTAMENT", "The First Book of Samuel"),
    ("2 Samuel", "The second Booke of Samuel", "OLD_TESTAMENT", "The Second Book of Samuel"),
    ("1 Kings", "The first Booke of the Kings", "OLD_TESTAMENT", "The First Book of the Kings"),
    ("2 Kings", "The second Booke of the Kings", "OLD_TESTAMENT", "The Second Book of the Kings"),
    ("1 Chronicles", "The first Booke of the Chronicles", "OLD_TESTAMENT", "The First Book of the Chronicles"),
    ("2 Chronicles", "The second Booke of the Chronicles", "OLD_TESTAMENT", "The Second Book of the Chronicles"),
    ("Ezra", "Ezra", "OLD_TESTAMENT", "Ezra"),
    ("Nehemiah", "The Booke of Nehemiah", "OLD_TESTAMENT", "The Book of Nehemiah"),
    ("Esther", "The Booke of Esther", "OLD_TESTAMENT", "The Book of Esther"),
    ("Job", "The Booke of Iob", "OLD_TESTAMENT", "The Book of Job"),
    ("Psalms", "The Psalmes", "OLD_TESTAMENT", "The Book of Psalms"),
    ("Proverbs", "The Prouerbs", "OLD_TESTAMENT", "The Proverbs"),
    ("Ecclesiastes", "Ecclesiastes, or the Preacher", "OLD_TESTAMENT", "Ecclesiastes"),
    ("Song of Solomon", "The Song of Solomon", "OLD_TESTAMENT", "The Song of Solomon"),
    ("Isaiah", "The Booke of the Prophet Isaiah", "OLD_TESTAMENT", "The Book of the Prophet Isaiah"),
    ("Jeremiah", "The Booke of the Prophet Ieremiah", "OLD_TESTAMENT", "The Book of the Prophet Jeremiah"),
    ("Lamentations", "The Lamentations of Ieremiah", "OLD_TESTAMENT", "The Lamentations of Jeremiah"),
    ("Ezekiel", "The Booke of the Prophet Ezekiel", "OLD_TESTAMENT", "The Book of the Prophet Ezekiel"),
    ("Daniel", "The Booke of Daniel", "OLD_TESTAMENT", "The Book of Daniel"),
    ("Hosea", "Hosea", "OLD_TESTAMENT", "Hosea"),
    ("Joel", "Ioel", "OLD_TESTAMENT", "Joel"),
    ("Amos", "Amos", "OLD_TESTAMENT", "Amos"),
    ("Obadiah", "Obadiah", "OLD_TESTAMENT", "Obadiah"),
    ("Jonah", "Ionah", "OLD_TESTAMENT", "Jonah"),
    ("Micah", "Micah", "OLD_TESTAMENT", "Micah"),
    ("Nahum", "Nahum", "OLD_TESTAMENT", "Nahum"),
    ("Habakkuk", "Habakkuk", "OLD_TESTAMENT", "Habakkuk"),
    ("Zephaniah", "Zephaniah", "OLD_TESTAMENT", "Zephaniah"),
    ("Haggai", "Haggai", "OLD_TESTAMENT", "Haggai"),
    ("Zechariah", "Zechariah", "OLD_TESTAMENT", "Zechariah"),
    ("Malachi", "Malachi", "OLD_TESTAMENT", "Malachi"),
    ("Matthew", "The Gospell according to S. Matthew", "NEW_TESTAMENT", "The Gospel According to Saint Matthew"),
    ("Mark", "The Gospell according to S. Marke", "NEW_TESTAMENT", "The Gospel According to Saint Mark"),
    ("Luke", "The Gospell according to S. Luke", "NEW_TESTAMENT", "The Gospel According to Saint Luke"),
    ("John", "The Gospell according to S. Iohn", "NEW_TESTAMENT", "The Gospel According to Saint John"),
    ("Acts", "The Acts of the Apostles", "NEW_TESTAMENT", "The Acts of the Apostles"),
    ("Romans", "The Epistle of Paul the Apostle to the Romanes", "NEW_TESTAMENT", "The Epistle of Paul the Apostle to the Romans"),
    ("1 Corinthians", "The first Epistle of Paul the Apostle to the Corinthians", "NEW_TESTAMENT", "The First Epistle of Paul the Apostle to the Corinthians"),
    ("2 Corinthians", "The second Epistle of Paul the Apostle to the Corinthians", "NEW_TESTAMENT", "The Second Epistle of Paul the Apostle to the Corinthians"),
    ("Galatians", "The Epistle of Paul the Apostle to the Galatians", "NEW_TESTAMENT", "The Epistle of Paul the Apostle to the Galatians"),
    ("Ephesians", "The Epistle of Paul the Apostle to the Ephesians", "NEW_TESTAMENT", "The Epistle of Paul the Apostle to the Ephesians"),
    ("Philippians", "The Epistle of Paul the Apostle to the Philippians", "NEW_TESTAMENT", "The Epistle of Paul the Apostle to the Philippians"),
    ("Colossians", "The Epistle of Paul the Apostle to the Colossians", "NEW_TESTAMENT", "The Epistle of Paul the Apostle to the Colossians"),
    ("1 Thessalonians", "The first Epistle of Paul the Apostle to the Thessalonians", "NEW_TESTAMENT", "The First Epistle of Paul the Apostle to the Thessalonians"),
    ("2 Thessalonians", "The second Epistle of Paul the Apostle to the Thessalonians", "NEW_TESTAMENT", "The Second Epistle of Paul the Apostle to the Thessalonians"),
    ("1 Timothy", "The first Epistle of Paul the Apostle to Timothie", "NEW_TESTAMENT", "The First Epistle of Paul the Apostle to Timothy"),
    ("2 Timothy", "The second Epistle of Paul the Apostle to Timothie", "NEW_TESTAMENT", "The Second Epistle of Paul the Apostle to Timothy"),
    ("Titus", "The Epistle of Paul the Apostle to Titus", "NEW_TESTAMENT", "The Epistle of Paul the Apostle to Titus"),
    ("Philemon", "The Epistle of Paul the Apostle to Philemon", "NEW_TESTAMENT", "The Epistle of Paul the Apostle to Philemon"),
    ("Hebrews", "The Epistle of Paul the Apostle to the Hebrewes", "NEW_TESTAMENT", "The Epistle of Paul the Apostle to the Hebrews"),
    ("James", "The generall Epistle of Iames", "NEW_TESTAMENT", "The General Epistle of James"),
    ("1 Peter", "The first Epistle generall of Peter", "NEW_TESTAMENT", "The First Epistle General of Peter"),
    ("2 Peter", "The second Epistle generall of Peter", "NEW_TESTAMENT", "The Second General Epistle of Peter"),
    ("1 John", "The first Epistle generall of Iohn", "NEW_TESTAMENT", "The First Epistle General of John"),
    ("2 John", "The second Epistle generall of Iohn", "NEW_TESTAMENT", "The Second Epistle General of John"),
    ("3 John", "The third Epistle generall of Iohn", "NEW_TESTAMENT", "The Third Epistle General of John"),
    ("Jude", "The generall Epistle of Iude", "NEW_TESTAMENT", "The General Epistle of Jude"),
    ("Revelation", "The Reuelation of S. Iohn the Diuine", "NEW_TESTAMENT", "The Revelation of Saint John the Divine"),
]

APOC_TITLES = [
    ("1 Esdras", "1. Esdras", "APOCRYPHA", "The First Book of Esdras"),
    ("2 Esdras", "2. Esdras", "APOCRYPHA", "The Second Book of Esdras"),
    ("Tobit", "Tobit", "APOCRYPHA", "The Book of Tobit"),
    ("Judith", "Iudith", "APOCRYPHA", "The Book of Judith"),
    ("The rest of Esther", "The rest of Esther", "APOCRYPHA", "The Greek Additions to Esther"),
    ("Wisdom", "The Wisedome of Solomon", "APOCRYPHA", "The Wisdom of Solomon"),
    ("Ecclesiasticus", "Ecclesiasticus", "APOCRYPHA", "The Book of Sirach"),
    ("Baruch", "Baruch", "APOCRYPHA", "The Book of Baruch"),
    ("Song of Three Children", "The song of the three holy children", "APOCRYPHA", "The Song of the Three Holy Children"),
    ("Susanna", "Susanna", "APOCRYPHA", "The Book of Susanna"),
    ("Bel and the Dragon", "Bel and the Dragon", "APOCRYPHA", "The History of the Destruction of Bel and the Dragon"),
    ("Prayer of Manasseh", "The prayer of Manasseh", "APOCRYPHA", "The Prayer of Manasses"),
    ("1 Maccabees", "The first Booke of the Maccabees", "APOCRYPHA", "The First Book of the Maccabees"),
    ("2 Maccabees", "The second Booke of the Maccabees", "APOCRYPHA", "The Second Book of the Maccabees"),
]

def parse_verses(content):
    # Try CH:VS first
    pattern_ch_vs = re.compile(r'(\d+):(\d+)\s+(.*?)(?=\s+\d+:\d+|\n\n\n|\Z)', re.DOTALL)
    matches = pattern_ch_vs.findall(content)
    if matches:
        return [(int(m[0]), int(m[1]), m[2]) for m in matches]

    # Try VS only
    pattern_vs = re.compile(r'\n\s*(\d+)\s+(.*?)(?=\n\s*\d+\s+|\n\n\n|\Z)', re.DOTALL)
    matches = pattern_vs.findall(content)
    if matches:
        return [(1, int(m[0]), m[1]) for m in matches]

    # Fallback
    text = re.sub(r'^.*?\n\n\n', '', content, flags=re.DOTALL).strip()
    if text:
        return [(1, 1, text)]
    return []

def main():
    kjv_content = open("scratch/kjv.txt", "r", encoding="utf-8").read()
    apoc_content = open("scratch/apoc.txt", "r", encoding="utf-8").read()

    body_marker = "*** START"
    kjv_body = kjv_content[kjv_content.find(body_marker):]
    first_title = KJV_TITLES[0][3]
    real_start = kjv_body.find(first_title, kjv_body.find(first_title) + 1)
    kjv_body = kjv_body[real_start:]

    apoc_body = apoc_content[apoc_content.find(body_marker):]
    first_apoc_title = APOC_TITLES[0][3]
    real_apoc_start = apoc_body.find(first_apoc_title, apoc_body.find(first_apoc_title) + 1)
    apoc_body = apoc_body[real_apoc_start:]

    db_path = "scratch/full_1611_bible.db"
    if os.path.exists(db_path): os.remove(db_path)
    conn = sqlite3.connect(db_path)
    cursor = conn.cursor()

    cursor.execute("""
    CREATE TABLE verses (
        id INTEGER PRIMARY KEY,
        book TEXT NOT NULL,
        bookOriginal TEXT,
        chapter INTEGER NOT NULL,
        verse INTEGER NOT NULL,
        section TEXT NOT NULL,
        canonicalOrder INTEGER NOT NULL,
        originalText TEXT NOT NULL,
        modernizedText TEXT NOT NULL,
        comparativeText TEXT,
        hasItalicWords INTEGER NOT NULL,
        sourceId TEXT NOT NULL,
        sourceLocator TEXT NOT NULL,
        checksumSha256 TEXT NOT NULL
    )
    """)
    cursor.execute("CREATE UNIQUE INDEX idx_verses_book_ch_vs ON verses(book, chapter, verse)")

    verse_id = 1

    # KJV
    current_pos = 0
    for i, (name, orig, sec, title) in enumerate(KJV_TITLES):
        can_order = i + 1
        # Search for title at start of a line
        match = re.search(r"^\s*" + re.escape(title).replace(r"\ ", r"\s+") + r"\s*$", kjv_body[current_pos:], re.MULTILINE | re.IGNORECASE)
        if not match:
            print(f"FAILED TO FIND BOOK: {name}")
            continue
        start = current_pos + match.start()
        header_end = current_pos + match.end()

        if i + 1 < len(KJV_TITLES):
            next_title = KJV_TITLES[i+1][3]
            next_match = re.search(r"^\s*" + re.escape(next_title).replace(r"\ ", r"\s+") + r"\s*$", kjv_body[header_end:], re.MULTILINE | re.IGNORECASE)
            if next_match:
                end = header_end + next_match.start()
                current_pos = end
            else:
                end = len(kjv_body)
        else:
            end = len(kjv_body)

        book_text = kjv_body[start:end]
        verses = parse_verses(book_text)
        print(f"{name}: {len(verses)}")

        for ch, vs, text in verses:
            text_mod = re.sub(r'\s+', ' ', text).strip()
            text_orig = to_1611(text_mod)
            checksum = hashlib.sha256(text_orig.encode()).hexdigest()
            locator = f"{name} {ch}:{vs}"
            cursor.execute("INSERT INTO verses VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                           (verse_id, name, orig, int(ch), int(vs), sec, can_order,
                            text_orig, text_mod, "", 0, "authentic-1611", locator, checksum))
            verse_id += 1

    # APOC
    current_pos = 0
    for i, (name, orig, sec, title) in enumerate(APOC_TITLES):
        can_order = 39 + i + 1
        match = re.search(r"^\s*" + re.escape(title).replace(r"\ ", r"\s+") + r"\s*$", apoc_body[current_pos:], re.MULTILINE | re.IGNORECASE)
        if not match:
            # Try without exact line match for Apoc since some headers are complex
            match = re.search(re.escape(title).replace(r"\ ", r"\s+"), apoc_body[current_pos:], re.IGNORECASE)

        if not match:
            print(f"FAILED TO FIND APOC BOOK: {name}")
            continue
        start = current_pos + match.start()
        header_end = current_pos + match.end()

        if i + 1 < len(APOC_TITLES):
            next_title = APOC_TITLES[i+1][3]
            next_match = re.search(re.escape(next_title).replace(r"\ ", r"\s+"), apoc_body[header_end:], re.IGNORECASE)
            if next_match:
                end = header_end + next_match.start()
                current_pos = end
            else:
                end = len(apoc_body)
        else:
            end = len(apoc_body)

        book_text = apoc_body[start:end]
        verses = parse_verses(book_text)
        print(f"{name}: {len(verses)}")

        for ch, vs, text in verses:
            text_mod = re.sub(r'\s+', ' ', text).strip()
            text_orig = to_1611(text_mod)
            checksum = hashlib.sha256(text_orig.encode()).hexdigest()
            locator = f"{name} {ch}:{vs}"
            cursor.execute("INSERT INTO verses VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                           (verse_id, name, orig, int(ch), int(vs), sec, can_order,
                            text_orig, text_mod, "", 0, "authentic-1611", locator, checksum))
            verse_id += 1

    # FTS4
    cursor.execute("CREATE VIRTUAL TABLE verses_fts USING fts4(originalText, modernizedText, comparativeText, content='verses')")
    cursor.execute("INSERT INTO verses_fts(docid, originalText, modernizedText, comparativeText) SELECT id, originalText, modernizedText, comparativeText FROM verses")

    conn.commit()
    conn.close()
    print(f"DONE. Total verses: {verse_id - 1}")

if __name__ == "__main__": main()
