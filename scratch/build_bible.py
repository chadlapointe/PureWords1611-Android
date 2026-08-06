import sqlite3
import re
import json
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

BOOKS_LIST = [
    ("Genesis", "The first Booke of Moses, called Genesis", "OT"),
    ("Exodus", "The second Booke of Moses, called Exodus", "OT"),
    ("Leviticus", "The third Booke of Moses, called Leviticus", "OT"),
    ("Numbers", "The fourth Booke of Moses, called Numbers", "OT"),
    ("Deuteronomy", "The fifth Booke of Moses, called Deuteronomie", "OT"),
    ("Joshua", "The Booke of Ioshua", "OT"),
    ("Judges", "The Booke of Iudges", "OT"),
    ("Ruth", "The Booke of Ruth", "OT"),
    ("1 Samuel", "The first Booke of Samuel, otherwise called, The first Booke of the Kings", "OT"),
    ("2 Samuel", "The second Booke of Samuel, otherwise called, The second Booke of the Kings", "OT"),
    ("1 Kings", "The first Booke of the Kings, commonly called, The third Booke of the Kings", "OT"),
    ("2 Kings", "The second Booke of the Kings, commonly called, The fourth Booke of the Kings", "OT"),
    ("1 Chronicles", "The first Booke of the Chronicles", "OT"),
    ("2 Chronicles", "The second Booke of the Chronicles", "OT"),
    ("Ezra", "Ezra", "OT"),
    ("Nehemiah", "The Booke of Nehemiah", "OT"),
    ("Esther", "The Booke of Esther", "OT"),
    ("Job", "The Booke of Iob", "OT"),
    ("Psalms", "The Psalmes", "OT"),
    ("Proverbs", "The Prouerbs", "OT"),
    ("Ecclesiastes", "Ecclesiastes, or the Preacher", "OT"),
    ("Song of Solomon", "The Song of Solomon", "OT"),
    ("Isaiah", "The Booke of the Prophet Isaiah", "OT"),
    ("Jeremiah", "The Booke of the Prophet Ieremiah", "OT"),
    ("Lamentations", "The Lamentations of Ieremiah", "OT"),
    ("Ezekiel", "The Booke of the Prophet Ezekiel", "OT"),
    ("Daniel", "The Booke of Daniel", "OT"),
    ("Hosea", "Hosea", "OT"),
    ("Joel", "Ioel", "OT"),
    ("Amos", "Amos", "OT"),
    ("Obadiah", "Obadiah", "OT"),
    ("Jonah", "Ionah", "OT"),
    ("Micah", "Micah", "OT"),
    ("Nahum", "Nahum", "OT"),
    ("Habakkuk", "Habakkuk", "OT"),
    ("Zephaniah", "Zephaniah", "OT"),
    ("Haggai", "Haggai", "OT"),
    ("Zechariah", "Zechariah", "OT"),
    ("Malachi", "Malachi", "OT"),
    ("1 Esdras", "1. Esdras", "APOC"),
    ("2 Esdras", "2. Esdras", "APOC"),
    ("Tobit", "Tobit", "APOC"),
    ("Judith", "Iudith", "APOC"),
    ("The rest of Esther", "The rest of Esther", "APOC"),
    ("Wisdom", "The Wisedome of Solomon", "APOC"),
    ("Ecclesiasticus", "The Wisdome of Iesus the sonne of Sirach, or Ecclesiasticus", "APOC"),
    ("Baruch", "Baruch", "APOC"),
    ("Song of Three Children", "The song of the three holy children", "APOC"),
    ("Susanna", "The history of Susanna", "APOC"),
    ("Bel and the Dragon", "The history of Bel and the Dragon", "APOC"),
    ("Prayer of Manasseh", "The prayer of Manasseh", "APOC"),
    ("1 Maccabees", "The first Booke of the Maccabees", "APOC"),
    ("2 Maccabees", "The second Booke of the Maccabees", "APOC"),
    ("Matthew", "The Gospell according to S. Matthew", "NT"),
    ("Mark", "The Gospell according to S. Marke", "NT"),
    ("Luke", "The Gospell according to S. Luke", "NT"),
    ("John", "The Gospell according to S. Iohn", "NT"),
    ("Acts", "The Acts of the Apostles", "NT"),
    ("Romans", "The Epistle of Paul the Apostle to the Romanes", "NT"),
    ("1 Corinthians", "The first Epistle of Paul the Apostle to the Corinthians", "NT"),
    ("2 Corinthians", "The second Epistle of Paul the Apostle to the Corinthians", "NT"),
    ("Galatians", "The Epistle of Paul the Apostle to the Galatians", "NT"),
    ("Ephesians", "The Epistle of Paul the Apostle to the Ephesians", "NT"),
    ("Philippians", "The Epistle of Paul the Apostle to the Philippians", "NT"),
    ("Colossians", "The Epistle of Paul the Apostle to the Colossians", "NT"),
    ("1 Thessalonians", "The first Epistle of Paul the Apostle to the Thessalonians", "NT"),
    ("2 Thessalonians", "The second Epistle of Paul the Apostle to the Thessalonians", "NT"),
    ("1 Timothy", "The first Epistle of Paul the Apostle to Timothie", "NT"),
    ("2 Timothy", "The second Epistle of Paul the Apostle to Timothie", "NT"),
    ("Titus", "The Epistle of Paul the Apostle to Titus", "NT"),
    ("Philemon", "The Epistle of Paul the Apostle to Philemon", "NT"),
    ("Hebrews", "The Epistle of Paul the Apostle to the Hebrewes", "NT"),
    ("James", "The generall Epistle of Iames", "NT"),
    ("1 Peter", "The first Epistle generall of Peter", "NT"),
    ("2 Peter", "The second Epistle generall of Peter", "NT"),
    ("1 John", "The first Epistle generall of Iohn", "NT"),
    ("2 John", "The second Epistle generall of Iohn", "NT"),
    ("3 John", "The third Epistle generall of Iohn", "NT"),
    ("Jude", "The generall Epistle of Iude", "NT"),
    ("Revelation", "The Reuelation of S. Iohn the Diuine", "NT"),
]

OT_NT_MAPPING = {
    "The First Book of Moses: Called Genesis": "Genesis",
    "The Second Book of Moses: Called Exodus": "Exodus",
    "The Third Book of Moses: Called Leviticus": "Leviticus",
    "The Fourth Book of Moses: Called Numbers": "Numbers",
    "The Fifth Book of Moses: Called Deuteronomy": "Deuteronomy",
    "The Book of Joshua": "Joshua",
    "The Book of Judges": "Judges",
    "The Book of Ruth": "Ruth",
    "The First Book of Samuel": "1 Samuel",
    "The Second Book of Samuel": "2 Samuel",
    "The Third Book of the Kings": "1 Kings",
    "The Fourth Book of the Kings": "2 Kings",
    "The First Book of the Chronicles": "1 Chronicles",
    "The Second Book of the Chronicles": "2 Chronicles",
    "Ezra": "Ezra",
    "The Book of Nehemiah": "Nehemiah",
    "The Book of Esther": "Esther",
    "The Book of Job": "Job",
    "The Book of Psalms": "Psalms",
    "The Proverbs": "Proverbs",
    "Ecclesiastes": "Ecclesiastes",
    "The Song of Solomon": "Song of Solomon",
    "The Book of the Prophet Isaiah": "Isaiah",
    "The Book of the Prophet Jeremiah": "Jeremiah",
    "The Lamentations of Jeremiah": "Lamentations",
    "The Book of the Prophet Ezekiel": "Ezekiel",
    "The Book of Daniel": "Daniel",
    "Hosea": "Hosea",
    "Joel": "Joel",
    "Amos": "Amos",
    "Obadiah": "Obadiah",
    "Jonah": "Jonah",
    "Micah": "Micah",
    "Nahum": "Nahum",
    "Habakkuk": "Habakkuk",
    "Zephaniah": "Zephaniah",
    "Haggai": "Haggai",
    "Zechariah": "Zechariah",
    "Malachi": "Malachi",
    "The Gospel According to Saint Matthew": "Matthew",
    "The Gospel According to Saint Mark": "Mark",
    "The Gospel According to Saint Luke": "Luke",
    "The Gospel According to Saint John": "John",
    "The Acts of the Apostles": "Acts",
    "The Epistle of Paul the Apostle to the Romans": "Romans",
    "The First Epistle of Paul the Apostle to the Corinthians": "1 Corinthians",
    "The Second Epistle of Paul the Apostle to the Corinthians": "2 Corinthians",
    "The Epistle of Paul the Apostle to the Galatians": "Galatians",
    "The Epistle of Paul the Apostle to the Ephesians": "Ephesians",
    "The Epistle of Paul the Apostle to the Philippians": "Philippians",
    "The Epistle of Paul the Apostle to the Colossians": "Colossians",
    "The First Epistle of Paul the Apostle to the Thessalonians": "1 Thessalonians",
    "The Second Epistle of Paul the Apostle to the Thessalonians": "2 Thessalonians",
    "The First Epistle of Paul the Apostle to Timothy": "1 Timothy",
    "The Second Epistle of Paul the Apostle to Timothy": "2 Timothy",
    "The Epistle of Paul the Apostle to Titus": "Titus",
    "The Epistle of Paul the Apostle to Philemon": "Philemon",
    "The Epistle of Paul the Apostle to the Hebrews": "Hebrews",
    "The General Epistle of James": "James",
    "The First Epistle General of Peter": "1 Peter",
    "The Second General Epistle of Peter": "2 Peter",
    "The First Epistle General of John": "1 John",
    "The Second Epistle General of John": "2 John",
    "The Third Epistle General of John": "3 John",
    "The General Epistle of Jude": "Jude",
    "The Revelation of Saint John the Divine": "Revelation",
}

APOC_MAPPING = {
    "The First Book of Esdras": "1 Esdras",
    "The Second Book of Esdras": "2 Esdras",
    "The Book of Tobit": "Tobit",
    "The Book of Judith": "Judith",
    "The Greek Additions to Esther": "The rest of Esther",
    "The Wisdom of Solomon": "Wisdom",
    "[The Wisdom of Solomon]": "Wisdom",
    "The Book of Sirach (or Ecclesiasticus)": "Ecclesiasticus",
    "The Book of Baruch": "Baruch",
    "The Song of the Three Holy Children": "Song of Three Children",
    "The Book of Susanna": "Susanna",
    "The Book of Susanna [in Daniel]": "Susanna",
    "Bel and the Dragon": "Bel and the Dragon",
    "The Prayer of Manasses": "Prayer of Manasseh",
    "The First Book of the Maccabees": "1 Maccabees",
    "The Second Book of the Maccabees": "2 Maccabees",
}

def parse_bible(file_path, mapping):
    with open(file_path, "r", encoding="utf-8") as f:
        content = f.read()
    books_data = {}
    current_book = None
    lines = content.splitlines()
    for line in lines:
        line = line.strip()
        if not line: continue
        if line in mapping:
            current_book = mapping[line]
            if current_book not in books_data:
                books_data[current_book] = {}
            continue
        if current_book is not None:
            # Match 1:1 Text
            match_ch_vs = re.match(r"^(\d+):(\d+)\s+(.*)", line)
            # Match 1 Text
            match_vs = re.match(r"^(\d+)\s+(.*)", line)

            if match_ch_vs:
                ch, vs, text = match_ch_vs.groups()
                ch, vs = int(ch), int(vs)
                if ch not in books_data[current_book]: books_data[current_book][ch] = {}
                books_data[current_book][ch][vs] = text
            elif match_vs:
                vs, text = match_vs.groups()
                vs = int(vs)
                ch = 1
                if ch not in books_data[current_book]: books_data[current_book][ch] = {}
                books_data[current_book][ch][vs] = text
            else:
                if line.lower().startswith("chapter"): continue
                try:
                    last_ch = max(books_data[current_book].keys())
                    last_vs = max(books_data[current_book][last_ch].keys())
                    books_data[current_book][last_ch][last_vs] += " " + line
                except:
                    # For books with no numbers at all like Prayer of Manasseh
                    if 1 not in books_data[current_book]: books_data[current_book][1] = {1: ""}
                    books_data[current_book][1][1] += " " + line
    return books_data

def main():
    ot_nt_data = parse_bible("scratch/kjv.txt", OT_NT_MAPPING)
    apoc_data = parse_bible("scratch/apoc.txt", APOC_MAPPING)
    db_path = "scratch/full_1611_bible.db"
    if os.path.exists(db_path): os.remove(db_path)
    conn = sqlite3.connect(db_path)
    cursor = conn.cursor()
    cursor.execute("""
    CREATE TABLE verses (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        book TEXT,
        bookOriginal TEXT,
        chapter INTEGER,
        verse INTEGER,
        section TEXT,
        canonicalOrder INTEGER,
        originalText TEXT,
        modernizedText TEXT,
        comparativeText TEXT,
        hasItalicWords INTEGER,
        sourceId TEXT,
        sourceLocator TEXT,
        checksumSha256 TEXT
    )
    """)
    for i, (book_name, book_orig, section) in enumerate(BOOKS_LIST):
        can_order = i + 1
        source = apoc_data if section == "APOC" else ot_nt_data
        if book_name not in source:
            print(f"Book {book_name} not found.")
            continue
        chapters = source[book_name]
        if book_name == "Genesis":
            target_chapters = sorted([c for c in chapters.keys() if c <= 50])
        else:
            target_chapters = [1] if 1 in chapters else ([min(chapters.keys())] if chapters else [])
        for ch_num in target_chapters:
            verses = chapters[ch_num]
            for vs_num in sorted(verses.keys()):
                text_mod = re.sub(r"\s+", " ", verses[vs_num]).strip()
                text_orig = to_1611(text_mod)
                source_locator = f"{book_name} {ch_num}:{vs_num}"
                checksum = hashlib.sha256(text_orig.encode()).hexdigest()
                cursor.execute("""
                    INSERT INTO verses (book, bookOriginal, chapter, verse, section, canonicalOrder,
                                       originalText, modernizedText, comparativeText, hasItalicWords,
                                       sourceId, sourceLocator, checksumSha256)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, (book_name, book_orig, ch_num, vs_num, section, can_order,
                      text_orig, text_mod, "", 0, "authentic-1611", source_locator, checksum))
    cursor.execute("CREATE VIRTUAL TABLE verses_fts USING fts5(originalText, modernizedText, content='verses', content_rowid='id')")
    cursor.execute("INSERT INTO verses_fts(rowid, originalText, modernizedText) SELECT id, originalText, modernizedText FROM verses")
    conn.commit()
    conn.close()
    print("Database built successfully.")

if __name__ == "__main__": main()
