# Overview

This is a fully functional campus event and location management system built in Java. It's an interactive console application that allows students to navigate campus maps, attend events, book facilities, and track their activity scores across sessions.

**Key Features:**
- Interactive campus navigation on a 2D grid-based map
- Event management (Lectures, Seminars, Exams) at various campus locations
- Score tracking system with session history persistence
- Place booking for Sports Centres and Event Halls
- Comprehensive data validation and error handling
- Support for multiple campus locations (Burnley, Hawthorn, Parkville, Shepparton, Southbank)

**Implementation Status:** ✅ Fully Complete - All features implemented and tested

# User Preferences

Preferred communication style: Simple, everyday language.

# Recent Changes (Latest Update)

**Date:** October 17, 2025

**Major Implementation Completed:**

1. **Event System** - Fully implemented event hierarchy with Lecture, Seminar, and Exam classes
   - Lectures have course codes and lecturers
   - Seminars support multiple speakers
   - Exams have course codes only

2. **SessionManager** - Tracks current session scores and previous session history
   - Manages score accumulation across student activities
   - Persists data to session_scores.txt file

3. **Complete File Handling** - Robust file I/O with comprehensive validation
   - Map file reader with 6 validation checks
   - Events file reader with 9 validation checks
   - Session scores reader and writer
   - Events file writer for unattended events

4. **Student Navigation** - Full movement system with exception handling
   - Supports 4-directional movement (Up, Down, Left, Right)
   - Throws MovementBlockedException for invalid moves
   - Tracks moves, hits, and cumulative scores

5. **Place Interactions** - Complete visiting and interaction logic
   - Cafeteria - shows menu
   - Library - study location
   - Sports Centre - shows facilities, bookable
   - Lecture/Event Halls - event attendance, bookable
   - Proper score calculation for places and events

6. **Main Menu Features** - All 6 options fully implemented
   - Visit campus (interactive navigation)
   - Print schedules (Cafeteria menu, Sports facilities, Event schedules)
   - Book places (Sports Centres and Event Halls)
   - View current session score history
   - View previous session scores
   - Save and exit (persists session data and unattended events)

7. **Bookable Interface** - Implemented for professional bookings
   - SportsCentre implements Bookable
   - EventHall implements Bookable

8. **Data Structures** - Converted from arrays to ArrayList throughout
   - CampusMap uses ArrayList<ArrayList<MapPosition>>
   - Events stored in ArrayList
   - Proper deep copying to prevent privacy leaks

**Project Structure:**
- `/src/` - All Java source code organized by package
  - `/events/` - Event class hierarchy
  - `/enums/` - MapPositionType and PlaceType enums
  - `/utils/` - Constants and Messages utilities
- `/data/` - Data files (maps, events, session_scores.txt)
- `compile.sh` - Compilation script
- `run.sh` - Example run script

# System Architecture

## Data Storage Architecture

**Problem**: Need to manage event and location data across multiple campuses with flexible querying capabilities.

**Solution**: Text file-based storage with CSV format organized by domain (events, maps, sessions).

**Structure**:
- `/data/events/` - Event files (events1.txt through events6.txt) containing academic event records
- `/data/maps/` - Campus location files (one per campus) defining facility grid layouts
- `/data/session_scores.txt` - Session tracking data (currently empty/unused)

**Rationale**: 
- Simple, human-readable format
- Easy version control and diff tracking
- No database infrastructure needed for initial implementation
- Suitable for small to medium datasets

**Considerations**: This architecture may need migration to a proper database (SQLite, PostgreSQL) as data volume grows or if complex querying becomes necessary.

## Data Model

**Events Schema**:
- Grid-based positioning: `row_id`, `col_id`
- Event metadata: `event_type` (lecture/exam/seminar), `date`, `start_time`, `end_time`
- Content: `name`, `speaker` (multiple speakers separated by #), `score`

**Maps Schema**:
- Grid-based positioning: `row_id`, `col_id`
- Facility metadata: `place_type`, `place_name`, `score`, `restricted` (yes/no)
- Place types: LECTURE_HALL, EVENT_HALL, CAFETERIA, SPORTS_CENTRE, LIBRARY

**Session Scores Schema** (currently empty):
- Tracking fields: `session_id`, `place_name`, `event_name`, `date`, `time_range`
- Metrics: `moves`, `hits`, `scores`

## Data Quality Issues

The current dataset contains several data quality problems that need handling:

1. **Missing values**: Some records have empty fields (empty strings or missing data)
2. **Invalid time formats**: Times like "24:00" and "31:00" exceed valid ranges
3. **Inconsistent event types**: Mix of "exam" and "exams", "EVENT_HALL" vs "EVENT_HALLS"
4. **Mixed data types**: "restricted" field shows inconsistent values (yes/no/empty, swapped with score in one case)
5. **Inconsistent place types**: "CAFE" vs "CAFETERIA"

**Design Decision**: The system should implement robust data validation and cleaning logic to handle these inconsistencies, potentially including:
- Time validation and normalization
- Enum validation for categorical fields
- Missing value handling strategies
- Data type enforcement

## Grid-Based Coordinate System

**Problem**: Need to map physical campus locations in a way that's simple to represent and query.

**Solution**: 2D grid coordinate system using `row_id` and `col_id`.

**Characteristics**:
- Rows appear to range from 1-10
- Columns appear to range from 1-8
- Multiple entities can occupy different grid positions
- Same coordinates may be reused across different campuses (files)

**Use Cases**:
- Spatial queries (finding nearby facilities)
- Event-to-location mapping
- Campus navigation
- Conflict detection (overlapping events at same location)

## Multi-Speaker Support

**Design Decision**: Speakers are stored as delimited strings using "#" separator.

**Example**: `Dr. Susan King#Dr. Emma Stone`

**Rationale**: 
- Simple implementation without complex relationships
- Preserves speaker order
- Easy to parse and display

**Alternative Considered**: Relational database with separate speakers table - rejected for initial simplicity, but may be needed later for speaker management features.

## Scoring System

Both events and locations use a `score` field, suggesting a rating or quality metric:

- **Events**: Scores range from ~6.5 to 9.2 (positive values)
- **Locations**: 
  - Positive scores for venues (6.4 to 9.1)
  - Negative scores for cafeterias (-13.0 to -2.0)

**Implication**: The scoring system likely represents quality ratings, with cafeterias using negative values possibly to discourage their selection in optimization algorithms (e.g., pathfinding, event placement).

## Campus Organization

**Structure**: Each campus is a separate file with its own facility layout:

- **Shepparton**: Largest campus with 27 facilities
- **Hawthorn**: 7 facilities
- **Southbank**: 6 facilities  
- **Burnley**: 3 facilities
- **Parkville**: No facilities defined (empty)

**Restriction System**: Binary `restricted` field controls access to facilities, likely for:
- Permission-based event scheduling
- Access control implementation
- Facility availability filtering

# External Dependencies

## Current Dependencies

**None identified** - The system currently operates on flat text files with no external services, APIs, or database systems.

## Potential Future Dependencies

Based on the architecture and data model, the following integrations may be considered:

1. **Database System**: 
   - PostgreSQL or SQLite for relational data management
   - Migration path: Convert CSV files to database tables with proper schemas and relationships
   - Benefits: ACID compliance, complex queries, data integrity constraints

2. **ORM Layer**:
   - Drizzle ORM (if TypeScript/JavaScript)
   - SQLAlchemy (if Python)
   - Purpose: Type-safe database interactions, migration management

3. **Calendar/Scheduling APIs**:
   - Google Calendar API
   - Microsoft Graph API
   - Purpose: Event synchronization, external calendar integration

4. **Data Validation**:
   - Zod (TypeScript)
   - Pydantic (Python)
   - Purpose: Schema validation, data cleaning, type safety

5. **Mapping/Visualization**:
   - Leaflet or Mapbox for interactive campus maps
   - D3.js for grid visualization
   - Purpose: Visual representation of grid-based campus layout