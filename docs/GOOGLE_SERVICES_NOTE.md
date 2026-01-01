# Google Services Configuration Note

The `google-services.json` file is currently in `.gitignore` (as is best practice for production apps).

However, for this setup, we've included a **placeholder** `google-services.json` file with setup instructions.

## Options:

### Option 1: Force-add the placeholder (Recommended for initial setup)
```bash
git add -f app/google-services.json
```

This will include the placeholder in the repository to help developers understand what's needed.

### Option 2: Keep it gitignored
Leave it in `.gitignore` and developers will need to:
1. Read the `docs/ANALYTICS_SETUP.md` guide
2. Create their own Firebase project
3. Download and add their own `google-services.json`

## Current Status
The placeholder file exists at `app/google-services.json` but is not tracked by git.

## Recommendation
For open-source projects or initial team setup, force-adding the placeholder is helpful. For production apps with real Firebase credentials, keep it in `.gitignore`.
