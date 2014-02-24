/**
 * Author: Matthias Stock
 */
var i18n = i18n || {};
i18n["id"] = {
    standard: "en",
    en: "en_US",
    de: "de_DE"
};
i18n["en_US"] = {
    version: "0.4",
    general: {
        color: "Color",
        label: "Label",
        node: "Node"
    },
    options: {
        data: {
            title: "Data",
            empty: "Click on a vertex to see additional information."
        },
        pfam: {
            title: "Pfam",
            empty: "Click on a vertex to query the Pfam database (pfam.sanger.ac.uk).",
            noresult: "No result."
        },
        ego: {
            title: "Ego Network",
            depth: "Depth",
            directionIgnore: "Ignore edge direction",
            applyLayout: "Apply a new layout to the network",
            select: "Please choose a vertex.",
            selected: "Selected",
            none: "none",
            add: "Add",
            clear: "Clear",
            submit: "Calculate",
            relayout: "Run Layout",
            lessthan: "if less than 100 nodes"
        },
        history: {
            back: "back",
            focusHistory: "Focus History",
            forward: "forward"
        },
        search: {
            term: "Search term",
            caption: "Search",
            noresult: "No search results found.",
            more: "[more results than displayed]"
        },
        log: {
            title: "Log",
            pfamQuery: "Pfam query",
            egoNetwork: "Ego network",
            ofDepth: "of depth",
            directionIgnored: "direction ignored",
            forNodes: "for"
        },
        language: {
            title: "Language",
            english: "English",
            german: "Deutsch"
        }
    }
};