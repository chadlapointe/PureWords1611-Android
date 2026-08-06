import sqlite3
import json
import hashlib
import os

def generate_db():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    db_name = os.path.join(script_dir, 'full_1611_bible.db')
    if os.path.exists(db_name):
        os.remove(db_name)

    conn = sqlite3.connect(db_name)
    cursor = conn.cursor()

    # Create verses table
    cursor.execute('''
        CREATE TABLE verses (
            id INTEGER PRIMARY KEY,
            book TEXT,
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
    ''')

    # Create FTS table (FTS5)
    # Using FTS5 as FTS4 was not available in the environment.
    # Content-backed FTS table
    cursor.execute('''
        CREATE VIRTUAL TABLE verses_fts USING fts5(
            originalText,
            modernizedText,
            content='verses',
            content_rowid='id'
        )
    ''')

    # Paths to JSON files relative to this script
    script_dir = os.path.dirname(os.path.abspath(__file__))
    json_files = [
        os.path.join(script_dir, '..', 'app', 'src', 'main', 'assets', 'study', 'verses_ot.json'),
        os.path.join(script_dir, '..', 'app', 'src', 'main', 'assets', 'study', 'verses_apoc.json'),
        os.path.join(script_dir, '..', 'app', 'src', 'main', 'assets', 'study', 'verses_nt.json')
    ]

    print(f"Reading files: {json_files}")

    for json_file in json_files:
        if not os.path.exists(json_file):
            print(f"File not found: {json_file}")
            continue

        with open(json_file, 'r', encoding='utf-8') as f:
            data = json.load(f)
            print(f"Processing {json_file} with {len(data)} entries...")
            for item in data:
                original_text = item.get('text_original_1611', '')
                # Calculate checksum if not provided in JSON
                checksum = item.get('checksum_sha256')
                if not checksum:
                    checksum = hashlib.sha256(original_text.encode('utf-8')).hexdigest()

                cursor.execute('''
                    INSERT INTO verses (
                        id, book, chapter, verse, section, canonicalOrder,
                        originalText, modernizedText, comparativeText,
                        hasItalicWords, sourceId, sourceLocator, checksumSha256
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ''', (
                    item.get('id'),
                    item.get('book_display_name'),
                    item.get('chapter'),
                    item.get('verse'),
                    item.get('testament_section'),
                    item.get('canonical_order'),
                    original_text,
                    item.get('text_modernized_spelling'),
                    item.get('text_comparative_esv'),
                    1 if item.get('has_italicized_words') else 0,
                    item.get('source_id'),
                    item.get('source_locator'),
                    checksum
                ))

    # Populate FTS table (rebuild from content table)
    cursor.execute("INSERT INTO verses_fts(verses_fts) VALUES('rebuild')")

    conn.commit()
    conn.close()
    print(f"Database {db_name} generated successfully at {os.path.join(script_dir, db_name)}")

if __name__ == '__main__':
    generate_db()
