# English Lenses - Implementation Plan

## Phase 1: Architecture, Dashboard & Navigation
- Setup project structure (MVVM, Navigation Compose).
- Create Main Dashboard UI with responsive design (List-Detail for tablet, NavigationBar for mobile).
- Implement basic navigation between modules.

## Phase 2: Quiz Studio
- Data model for quizzes (Questions, Options, Feedback).
- Quiz creation UI (Manual & AI generation trigger).
- Interactive Quiz Player (Presentation mode, scoring).

## Phase 3: AI Lesson Generator
- Secure Gemini API integration using Firebase AI Logic.
- Lesson Generator UI (Topic input, level selector).
- Section-based editing/regeneration.

## Phase 4: AI Video Studio
- Video studio module using browser-supported technologies (Canvas/MediaRecorder).
- Scene timeline, editor, and preview.
- Export mechanism (WebM).

## Phase 5: Worksheet & Poster Studio
- Design templates for A4/Poster layouts.
- Dynamic PDF generation.
- Teacher watermark implementation.

## Phase 6: Voice & Listening Studio
- Text-to-speech integration.
- Listening activity generation.
- Audio playback UI.

## Phase 7: Persistence & Project Management
- Firestore storage for project state.
- Project Library (List, Rename, Duplicate, Delete).
- Final Polish & Testing.
