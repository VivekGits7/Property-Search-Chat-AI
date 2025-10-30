import React, { useState, useEffect, useRef } from "react";
import { motion, AnimatePresence } from "framer-motion";

export default function ChatApp() {
    const [notes, setNotes] = useState(
        `💡 Purpose:
This AI helps you search properties and projects using simple natural language — but it’s not a full GPT model. 
It’s a lightweight AI trained only on 83 property entries from a local dataset.
So, to get the best results, your query must be specific, well-structured, and spelled correctly.

________________________________________

⚙️ How to Search Correctly
✅ Follow this format for best results:
BHK PROPERTY_TYPE apartment located in FULLADDRESS, near LANDMARK.
It is FURNISHED_TYPE and currently STATUS, featuring NU BATHROOMS and NU BALCONIES,
priced at around ₹NU Crore/Lakh/K etc.

________________________________________

🏠 Field Details
BHK → 1, 2, 3, 4
PROPERTY_TYPE → Residential, Commercial, Any
FULLADDRESS → Example: Sindhi Society, Near Swami Vivekanand Jr College, Chembur, Mumbai Harbour, Mumbai
LANDMARK → Example: JBCN International School Mulund
FURNISHED_TYPE → Unfurnished, Furnished, Semi-Furnished, None
STATUS → Ready to Move, Under Construction
BATHROOMS → 1, 2, 3, 4
BALCONIES → 1, 2, 3, 4
PRICE → ₹NU Crore / Lakh / K

________________________________________

✅ Tip:
Use clear and complete details for address, landmark, and price.
Avoid vague terms like cheap flat or low price apartment.

📍 Example of a perfect search:
1BHK residential apartment located in Mumbai Chembur, near Babys School. 
It is unfurnished and currently under construction, featuring 1 bathroom and 1 balcony, 
priced at around ₹1.1 Crore.

________________________________________

⚠️ Important Guidelines
• ✅ Always mention BHK at the start.
• ✅ Add city and locality name (e.g., Mumbai Chembur).
• ✅ Include bathroom and balcony count.
• ✅ Write the price at the end in clear format (₹1.1 Crore / ₹85 Lakh / ₹90L).
• ✅ Use full sentences and correct spelling — the AI is regex-based, not semantic like ChatGPT.
• 🚫 Avoid shorthand, typos, or vague inputs (e.g., “cheap flat Mumbai”).
• 🚫 Don’t skip important words like “ready to move”, “under construction”, or “unfurnished”.

________________________________________

🧠 Why Precision Matters
This AI runs on a custom model with limited parameters, not billions like normal GPTs.
It only matches patterns from a small dataset (83 property entries) — so every keyword you type helps it understand:
• The location
• The price range
• The BHK type
• The furnishing and readiness status

🎯 Being precise ensures the AI can filter and summarize your query accurately.

________________________________________

📘 Example Prompts

1️⃣ Om Makarand Heights
2BHK residential apartment located at 104, Yashvant Seth Jadhav Marg, Gauri Shankar Wadi No. 2, Savitribai Phule Nagar, Pant Nagar, Ghatkopar East, Mumbai, Maharashtra 400075, near Hind High School.
It is unfurnished and currently under construction, featuring 2 bathrooms and 2 balconies, priced at around ₹1.4 crore.

________________________________________

2️⃣ Sainath Vrindavan (1BHK)
1BHK residential apartment located at Prataprao Gujar Rd, Neelam Nagar, Mulund East, Mumbai, Maharashtra 400081, near JBCN International School Mulund.
It is unfurnished and currently under construction, featuring 1 bathroom and 1 balcony, priced at around ₹1.2 crore.

________________________________________

3️⃣ Sainath Vrindavan (2BHK)
2BHK residential apartment located at Prataprao Gujar Rd, Neelam Nagar, Mulund East, Mumbai, Maharashtra 400081, near JBCN International School Mulund.
It is unfurnished and currently under construction, featuring 2 bathrooms and 2 balconies, priced at around ₹1.7 crore.

________________________________________

4️⃣ Sainath Vrindavan (2BHK)
2BHK residential apartment located at Prataprao Gujar Rd, Neelam Nagar, Mulund East, Mumbai, Maharashtra 400081, near JBCN International School Mulund.
It is unfurnished and currently under construction, featuring 2 bathrooms and 2 balconies, priced at around ₹1.9 crore.

________________________________________

5️⃣ Ashwini Apartments
Ashwini is a 1BHK residential apartment located in Mumbai Chembur, near Babys School.
It is unfurnished and currently under construction, featuring 1 bathroom and 1 balcony, priced at around ₹1.1 crore.

________________________________________

6️⃣ Sai Krupa Residency
2BHK residential apartment located in Sr. No. 13, beside Godrej, near Sai Nagar.
It is unfurnished and ready to move in, featuring 12 bathrooms and 3 balconies, priced at around ₹12 crore.

________________________________________

🌐 Backend Info:
- Endpoint: POST http://localhost:8083/api/search
- Response includes: extractedFilters, summary, cards
- Use clear, short prompts for better results.
- Example prompt: "Ashwini is a 1BHK residential apartment located in Mumbai Chembur, near Babys School..."`
    );

    const [notesExpanded, setNotesExpanded] = useState(false);
    const [input, setInput] = useState("");
    const [messages, setMessages] = useState([]);
    const [loading, setLoading] = useState(false);
    const [expandedCard, setExpandedCard] = useState(null);
    const messagesEndRef = useRef(null);
    const notesRef = useRef(null);

    // 🌀 Smooth scroll to bottom
    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({
            behavior: "smooth",
            block: "end",
        });
    };

    // 🌀 Scroll to notes when expanded
    useEffect(() => {
        if (notesExpanded && notesRef.current) {
            notesRef.current.scrollIntoView({ behavior: "smooth", block: "start" });
        }
    }, [notesExpanded]);

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    // 📨 Handle user query send
    async function handleSend() {
        if (!input.trim()) return;
        const userMessage = { role: "user", content: input };
        setMessages((m) => [...m, userMessage]);
        setInput("");
        setLoading(true);

        try {
            const res = await fetch("http://localhost:8083/api/search", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ query: input }),
            });
            if (!res.ok) throw new Error(`Server returned ${res.status}`);
            const data = await res.json();

            const botMessage = {
                role: "bot",
                content: data.summary || "Response received",
                raw: data,
            };
            setMessages((m) => [...m, botMessage]);
        } catch (err) {
            const errMessage = { role: "bot", content: `Error: ${err.message}` };
            setMessages((m) => [...m, errMessage]);
        } finally {
            setLoading(false);
        }
    }

    // 🏡 Handle fetching all properties
    async function handleAllProperties() {
        setLoading(true);
        try {
            const res = await fetch("http://localhost:8083/api/allProperties");
            if (!res.ok) throw new Error(`Server returned ${res.status}`);
            const data = await res.json();

            const botMessage = {
                role: "bot",
                content: "Here are all available properties:",
                raw: { cards: data },
            };

            setMessages((m) => [...m, botMessage]);
        } catch (err) {
            const errMessage = { role: "bot", content: `Error: ${err.message}` };
            setMessages((m) => [...m, errMessage]);
        } finally {
            setLoading(false);
        }
    }

    function renderObjectAsTable(obj) {
        if (!obj || typeof obj !== "object") return <span>{String(obj)}</span>;
        const entries = Object.entries(obj);
        return (
            <div className="w-full overflow-x-auto">
                <table className="min-w-full text-left table-auto">
                    <tbody>
                    {entries.map(([k, v]) => (
                        <tr key={k} className="border-b border-gray-800 even:bg-gray-900">
                            <td className="py-2 px-3 align-top font-medium text-sm w-40">
                                {k}
                            </td>
                            <td className="py-2 px-3 text-sm">
                                {Array.isArray(v) ? (
                                    <div className="space-y-2">
                                        {v.map((item, idx) => (
                                            <div
                                                key={idx}
                                                className="p-3 rounded-lg bg-gray-800 animate-fadeIn"
                                            >
                                                {typeof item === "object"
                                                    ? renderObjectAsTable(item)
                                                    : String(item)}
                                            </div>
                                        ))}
                                    </div>
                                ) : typeof v === "object" ? (
                                    renderObjectAsTable(v)
                                ) : (
                                    String(v)
                                )}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        );
    }

    return (
        <div className="min-h-screen bg-gray-900 text-gray-100 p-6 flex flex-col gap-6 relative">
            {/* Header */}
            <header className="flex items-center justify-between">
                <div>
                    <h1 className="text-2xl font-semibold">Property Finder</h1>
                    <p className="text-sm text-gray-400">
                        Your Smart AI Partner for Effortless Property Discovery.
                    </p>
                </div>
                <div className="flex items-center gap-3">
                    <a
                        href="https://www.linkedin.com/in/vivek-vishwakarma-/"
                        target="_blank"
                        rel="noreferrer"
                        className="px-4 py-2 rounded-md border border-gray-700 text-sm hover:bg-gray-800"
                    >
                        LinkedIn
                    </a>
                    <a
                        href="https://github.com/VivekGits7"
                        target="_blank"
                        rel="noreferrer"
                        className="px-4 py-2 rounded-md border border-gray-700 text-sm hover:bg-gray-800"
                    >
                        GitHub
                    </a>
                </div>
            </header>

            {/* Notes Section (Improved UI + Scrollable) */}
            <section
                ref={notesRef}
                className="bg-gray-800 rounded-2xl p-4 border border-gray-700 shadow-lg"
            >
                <div className="flex items-start justify-between gap-4">
                    <div className="flex-1">
                        <div className="flex items-center justify-between gap-3">
                            <h2 className="font-medium text-lg text-indigo-400">
                                ⚡ Read This Before You Ask! Discover how to get the best results from your AI Property Chat Assistant — the smarter way to find your dream home in seconds!
                            </h2>
                            <button
                                onClick={() => setNotesExpanded((s) => !s)}
                                className="text-sm px-3 py-1 rounded-md border border-gray-700 bg-gray-900 hover:bg-gray-700 transition-all"
                            >
                                {notesExpanded ? "Collapse" : "Expand"}
                            </button>
                        </div>

                        <div
                            className={`mt-3 transition-all duration-500 ease-in-out rounded-xl border border-gray-700 bg-gray-900 shadow-inner ${
                                notesExpanded ? "max-h-[400px]" : "max-h-24"
                            } overflow-hidden`}
                        >
                            <div
                                className={`overflow-y-auto ${
                                    notesExpanded ? "max-h-[400px] custom-scroll" : "max-h-24"
                                }`}
                            >
                                <pre className="whitespace-pre-wrap text-sm leading-relaxed font-mono text-gray-200 p-3">
                                    {notes}
                                </pre>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            {/* Chat Section */}
            <main className="flex-1 flex flex-col gap-4 pb-36 overflow-hidden">
                <div className="flex-1 overflow-auto rounded-xl border border-gray-800 p-4 flex flex-col gap-4">
                    {messages.length === 0 && (
                        <div className="text-center text-gray-500">
                            No messages yet — ask something and press Send.
                        </div>
                    )}

                    <AnimatePresence>
                        {messages.map((m, i) => (
                            <motion.div
                                key={i}
                                initial={{ opacity: 0, y: 10 }}
                                animate={{ opacity: 1, y: 0 }}
                                exit={{ opacity: 0, y: -10 }}
                                transition={{ duration: 0.3 }}
                                className={`flex ${
                                    m.role === "user" ? "justify-end" : "justify-start"
                                }`}
                            >
                                <div
                                    className={`max-w-3xl w-full ${
                                        m.role === "user"
                                            ? "bg-indigo-700 text-white"
                                            : "bg-gray-800 text-gray-100"
                                    } p-4 rounded-lg shadow-sm border border-gray-700`}
                                >
                                    <div className="text-sm mb-2 font-medium">
                                        {m.role === "user" ? "You" : "Assistant"}
                                    </div>
                                    <div className="text-sm mb-2">{m.content}</div>

                                    {m.role === "bot" && m.raw && (
                                        <div className="mt-3 space-y-4">
                                            {m.raw.extractedFilters && (
                                                <div>
                                                    <div className="text-sm mb-2 font-semibold">
                                                        Extracted Filters
                                                    </div>
                                                    <div className="rounded-lg border border-gray-700 p-3 bg-gray-900">
                                                        {renderObjectAsTable(
                                                            m.raw.extractedFilters
                                                        )}
                                                    </div>
                                                </div>
                                            )}

                                            {m.raw.summary && (
                                                <div>
                                                    <div className="text-sm mb-2 font-semibold">
                                                        Summary
                                                    </div>
                                                    <div className="rounded-lg border border-gray-700 p-3 bg-gray-900 text-sm">
                                                        {m.raw.summary}
                                                    </div>
                                                </div>
                                            )}

                                            {m.raw.cards &&
                                                Array.isArray(m.raw.cards) && (
                                                    <div>
                                                        <div className="text-sm mb-2 font-semibold">
                                                            Cards
                                                        </div>
                                                        <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
                                                            {m.raw.cards.map((card, idx) => (
                                                                <div
                                                                    key={idx}
                                                                    className="p-3 rounded-xl border border-gray-700 bg-gray-900 relative"
                                                                >
                                                                    <div className="font-semibold text-lg">
                                                                        {card.projectName || "—"}
                                                                    </div>
                                                                    <div className="text-sm mt-1">
                                                                        {card.fullAddress || "—"}{" "}
                                                                        {card.landmark
                                                                            ? ` — ${card.landmark}`
                                                                            : ""}
                                                                    </div>

                                                                    <div className="mt-2 text-sm grid grid-cols-2 gap-2">
                                                                        <div className="text-xs">BHK</div>
                                                                        <div className="text-xs font-medium">
                                                                            {card.bhk || "—"}
                                                                        </div>
                                                                        <div className="text-xs">
                                                                            Bathrooms
                                                                        </div>
                                                                        <div className="text-xs font-medium">
                                                                            {card.bathrooms ?? "—"}
                                                                        </div>
                                                                        <div className="text-xs">
                                                                            Balcony
                                                                        </div>
                                                                        <div className="text-xs font-medium">
                                                                            {card.balcony ?? "—"}
                                                                        </div>
                                                                        <div className="text-xs">
                                                                            Property Type
                                                                        </div>
                                                                        <div className="text-xs font-medium">
                                                                            {card.propertyType ||
                                                                                "RESIDENTIAL"}
                                                                        </div>
                                                                        <div className="text-xs">
                                                                            Furnishing
                                                                        </div>
                                                                        <div className="text-xs font-medium">
                                                                            {card.furnishedType || "—"}
                                                                        </div>
                                                                        <div className="text-xs">Status</div>
                                                                        <div className="text-xs font-medium">
                                                                            {card.status || "—"}
                                                                        </div>
                                                                        <div className="text-xs">Price</div>
                                                                        <div className="text-xs font-medium">
                                                                            {card.price
                                                                                ? `₹${card.price.toLocaleString()}`
                                                                                : "—"}
                                                                        </div>
                                                                    </div>

                                                                    <div
                                                                        className="mt-3 text-indigo-400 text-xs cursor-pointer hover:underline"
                                                                        onClick={() =>
                                                                            setExpandedCard(
                                                                                expandedCard === idx
                                                                                    ? null
                                                                                    : idx
                                                                            )
                                                                        }
                                                                    >
                                                                        {expandedCard === idx
                                                                            ? "Hide Slug"
                                                                            : "Show Slug"}
                                                                    </div>

                                                                    <AnimatePresence>
                                                                        {expandedCard === idx && (
                                                                            <motion.div
                                                                                initial={{
                                                                                    opacity: 0,
                                                                                    height: 0,
                                                                                }}
                                                                                animate={{
                                                                                    opacity: 1,
                                                                                    height: "auto",
                                                                                }}
                                                                                exit={{
                                                                                    opacity: 0,
                                                                                    height: 0,
                                                                                }}
                                                                                transition={{
                                                                                    duration: 0.3,
                                                                                }}
                                                                                className="mt-3 rounded-lg border border-gray-700 bg-gray-800 p-3 text-xs font-mono text-gray-300"
                                                                            >
                                                                                {card.ctaSlug ? (
                                                                                    <code>
                                                                                        {card.ctaSlug}
                                                                                    </code>
                                                                                ) : (
                                                                                    <span className="text-gray-500">
                                                                                        No slug available
                                                                                    </span>
                                                                                )}
                                                                            </motion.div>
                                                                        )}
                                                                    </AnimatePresence>
                                                                </div>
                                                            ))}
                                                        </div>
                                                    </div>
                                                )}
                                        </div>
                                    )}
                                </div>
                            </motion.div>
                        ))}
                    </AnimatePresence>

                    <div ref={messagesEndRef} />
                </div>
            </main>

            {/* ✅ Sticky Input Section */}
            <div className="fixed bottom-0 left-0 right-0 bg-gray-900 p-4 border-t border-gray-800">
                <div className="max-w-7xl mx-auto flex gap-2">
                    <textarea
                        value={input}
                        onChange={(e) => setInput(e.target.value)}
                        rows={2}
                        placeholder="Type your query here..."
                        className="flex-1 bg-gray-800 text-sm p-3 rounded-lg border border-gray-700"
                    />
                    <div className="flex flex-col gap-2">
                        <button
                            onClick={handleSend}
                            disabled={loading}
                            className="px-4 py-2 rounded-lg bg-indigo-600 hover:bg-indigo-700 disabled:opacity-60 transition-all"
                        >
                            {loading ? "Sending..." : "Send"}
                        </button>

                        <button
                            onClick={handleAllProperties}
                            className="px-4 py-2 rounded-lg bg-green-600 hover:bg-green-700 text-white text-sm transition-all"
                        >
                            All Properties
                        </button>

                        <button
                            onClick={() => setMessages([])}
                            className="px-4 py-2 rounded-lg bg-red-600 hover:bg-red-700 text-white text-sm transition-all"
                        >
                            Clear Chat
                        </button>
                    </div>
                </div>
            </div>

            <footer className="text-xs text-gray-500 mt-4">
                Made with ❤️ By Vivek Vishwakarma — copy into your React + Tailwind
                project and run. Backend must be running at
                http://localhost:8083/api/search
            </footer>
        </div>
    );
}
