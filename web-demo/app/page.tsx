"use client";

import { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import {
  Bell,
  Calendar,
  MapPin,
  User,
  LogOut,
  Plus,
  Home,
  Search,
  Settings,
  ChevronRight,
  AlertTriangle,
  BookOpen,
  Trophy,
  Users,
  Clock,
  Filter,
  Send,
  Image as ImageIcon,
  Mic,
  Video,
  BarChart3,
  Shield,
  Megaphone,
  CheckCircle,
  X,
  Eye,
  GraduationCap,
} from "lucide-react";

// Types
type UserRole = "student" | "admin";
type Screen =
  | "splash"
  | "login"
  | "signup"
  | "studentDashboard"
  | "adminDashboard"
  | "alerts"
  | "discover"
  | "profile"
  | "eventDetails"
  | "createAlert";

interface Alert {
  id: string;
  title: string;
  description: string;
  category: string;
  date: string;
  time: string;
  location: string;
  priority: "high" | "medium" | "low";
  image?: string;
}

// Sample data
const sampleAlerts: Alert[] = [
  {
    id: "1",
    title: "Final Exam Schedule Released",
    description:
      "The final examination schedule for Spring 2024 has been released. Please check your student portal for your personalized schedule.",
    category: "Academic",
    date: "May 15, 2024",
    time: "9:00 AM",
    location: "Main Campus",
    priority: "high",
  },
  {
    id: "2",
    title: "Campus Career Fair",
    description:
      "Join us for the annual career fair featuring 50+ top companies. Bring your resume and dress professionally!",
    category: "Event",
    date: "May 20, 2024",
    time: "10:00 AM - 4:00 PM",
    location: "Student Center Hall A",
    priority: "medium",
  },
  {
    id: "3",
    title: "Library Extended Hours",
    description:
      "During finals week, the library will be open 24/7 to support your study needs.",
    category: "Announcement",
    date: "May 10-25, 2024",
    time: "24/7",
    location: "University Library",
    priority: "low",
  },
  {
    id: "4",
    title: "Emergency: Building Evacuation Drill",
    description:
      "Mandatory evacuation drill scheduled. Please follow all safety protocols and proceed to designated assembly points.",
    category: "Emergency",
    date: "May 18, 2024",
    time: "2:00 PM",
    location: "All Campus Buildings",
    priority: "high",
  },
];

// Phone Frame Component
function PhoneFrame({ children }: { children: React.ReactNode }) {
  return (
    <div className="relative mx-auto w-[375px] h-[812px] bg-black rounded-[50px] p-3 shadow-2xl">
      {/* Notch */}
      <div className="absolute top-0 left-1/2 -translate-x-1/2 w-40 h-7 bg-black rounded-b-3xl z-50" />
      {/* Screen */}
      <div className="relative w-full h-full bg-white rounded-[38px] overflow-hidden">
        {/* Status Bar */}
        <div className="absolute top-0 left-0 right-0 h-11 bg-transparent z-40 flex items-center justify-between px-8 pt-2">
          <span className="text-xs font-medium text-foreground">9:41</span>
          <div className="flex items-center gap-1">
            <div className="w-4 h-2 border border-foreground rounded-sm">
              <div className="w-3/4 h-full bg-foreground rounded-sm" />
            </div>
          </div>
        </div>
        {/* Content */}
        <div className="phone-content h-full overflow-y-auto pt-11">
          {children}
        </div>
      </div>
    </div>
  );
}

// Splash Screen
function SplashScreen({ onComplete }: { onComplete: () => void }) {
  useEffect(() => {
    const timer = setTimeout(onComplete, 2000);
    return () => clearTimeout(timer);
  }, [onComplete]);

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      className="h-full bg-gradient-to-br from-primary to-primary-dark flex flex-col items-center justify-center"
    >
      <motion.div
        initial={{ scale: 0 }}
        animate={{ scale: 1 }}
        transition={{ type: "spring", duration: 0.8 }}
        className="w-24 h-24 bg-white rounded-3xl flex items-center justify-center shadow-lg mb-6"
      >
        <Bell className="w-12 h-12 text-primary" />
      </motion.div>
      <motion.h1
        initial={{ y: 20, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.3 }}
        className="text-3xl font-bold text-white mb-2"
      >
        College Alert
      </motion.h1>
      <motion.p
        initial={{ y: 20, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.5 }}
        className="text-white/80 text-sm"
      >
        Stay Connected. Stay Informed.
      </motion.p>
      <motion.div
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 1 }}
        className="mt-8"
      >
        <div className="w-8 h-8 border-2 border-white/30 border-t-white rounded-full animate-spin" />
      </motion.div>
    </motion.div>
  );
}

// Login Screen
function LoginScreen({
  onLogin,
  onSignUp,
}: {
  onLogin: (role: UserRole) => void;
  onSignUp: () => void;
}) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  return (
    <div className="h-full bg-white flex flex-col">
      <div className="flex-1 flex flex-col justify-center px-6 pb-8">
        <div className="text-center mb-8">
          <div className="w-16 h-16 bg-primary/10 rounded-2xl flex items-center justify-center mx-auto mb-4">
            <GraduationCap className="w-8 h-8 text-primary" />
          </div>
          <h1 className="text-2xl font-bold text-foreground">Welcome Back</h1>
          <p className="text-muted text-sm mt-1">Sign in to continue</p>
        </div>

        <div className="space-y-4">
          <div>
            <label className="text-sm font-medium text-foreground block mb-1.5">
              Email
            </label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="student@college.edu"
              className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-primary focus:ring-2 focus:ring-primary/20 outline-none transition text-sm"
            />
          </div>
          <div>
            <label className="text-sm font-medium text-foreground block mb-1.5">
              Password
            </label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Enter password"
              className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-primary focus:ring-2 focus:ring-primary/20 outline-none transition text-sm"
            />
          </div>

          <div className="text-right">
            <button className="text-sm text-primary font-medium">
              Forgot Password?
            </button>
          </div>

          <div className="pt-2 space-y-3">
            <button
              onClick={() => onLogin("student")}
              className="w-full py-3.5 bg-primary text-white rounded-xl font-semibold hover:bg-primary-dark transition shadow-lg shadow-primary/25"
            >
              Sign In as Student
            </button>
            <button
              onClick={() => onLogin("admin")}
              className="w-full py-3.5 bg-secondary text-white rounded-xl font-semibold hover:bg-secondary/90 transition shadow-lg shadow-secondary/25"
            >
              Sign In as Admin
            </button>
          </div>
        </div>

        <div className="mt-8 text-center">
          <p className="text-muted text-sm">
            Don&apos;t have an account?{" "}
            <button onClick={onSignUp} className="text-primary font-semibold">
              Sign Up
            </button>
          </p>
        </div>
      </div>
    </div>
  );
}

// Sign Up Screen
function SignUpScreen({
  onSignUp,
  onBack,
}: {
  onSignUp: (role: UserRole) => void;
  onBack: () => void;
}) {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isAdmin, setIsAdmin] = useState(false);
  const [school, setSchool] = useState("");

  return (
    <div className="h-full bg-white flex flex-col">
      <div className="px-4 py-3 flex items-center border-b border-gray-100">
        <button onClick={onBack} className="p-2 -ml-2">
          <ChevronRight className="w-5 h-5 rotate-180 text-foreground" />
        </button>
        <h1 className="text-lg font-semibold flex-1 text-center pr-7">
          Create Account
        </h1>
      </div>

      <div className="flex-1 px-6 py-6 overflow-auto">
        <div className="space-y-4">
          <div>
            <label className="text-sm font-medium text-foreground block mb-1.5">
              Full Name
            </label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="John Doe"
              className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-primary focus:ring-2 focus:ring-primary/20 outline-none transition text-sm"
            />
          </div>

          <div>
            <label className="text-sm font-medium text-foreground block mb-1.5">
              Email
            </label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="student@college.edu"
              className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-primary focus:ring-2 focus:ring-primary/20 outline-none transition text-sm"
            />
          </div>

          <div>
            <label className="text-sm font-medium text-foreground block mb-1.5">
              School/College
            </label>
            <select
              value={school}
              onChange={(e) => setSchool(e.target.value)}
              className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-primary focus:ring-2 focus:ring-primary/20 outline-none transition text-sm bg-white"
            >
              <option value="">Select your school</option>
              <option value="engineering">School of Engineering</option>
              <option value="business">School of Business</option>
              <option value="arts">School of Arts & Sciences</option>
              <option value="medicine">School of Medicine</option>
            </select>
          </div>

          <div>
            <label className="text-sm font-medium text-foreground block mb-1.5">
              Password
            </label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Create password"
              className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-primary focus:ring-2 focus:ring-primary/20 outline-none transition text-sm"
            />
          </div>

          <div className="flex items-center gap-3 p-4 bg-gray-50 rounded-xl">
            <input
              type="checkbox"
              id="isAdmin"
              checked={isAdmin}
              onChange={(e) => setIsAdmin(e.target.checked)}
              className="w-5 h-5 rounded border-gray-300 text-primary focus:ring-primary"
            />
            <label htmlFor="isAdmin" className="flex-1">
              <span className="text-sm font-medium text-foreground block">
                Register as Admin
              </span>
              <span className="text-xs text-muted">
                For faculty and staff only
              </span>
            </label>
            <Shield className="w-5 h-5 text-muted" />
          </div>

          <button
            onClick={() => onSignUp(isAdmin ? "admin" : "student")}
            className="w-full py-3.5 bg-primary text-white rounded-xl font-semibold hover:bg-primary-dark transition shadow-lg shadow-primary/25 mt-4"
          >
            Create Account
          </button>
        </div>
      </div>
    </div>
  );
}

// Student Dashboard
function StudentDashboard({
  onNavigate,
  alerts,
}: {
  onNavigate: (screen: Screen, data?: Alert) => void;
  alerts: Alert[];
}) {
  const [filter, setFilter] = useState("all");

  const filteredAlerts =
    filter === "all"
      ? alerts
      : alerts.filter((a) => a.category.toLowerCase() === filter);

  const categories = [
    { id: "all", label: "All", icon: Home },
    { id: "academic", label: "Academic", icon: BookOpen },
    { id: "event", label: "Events", icon: Calendar },
    { id: "emergency", label: "Emergency", icon: AlertTriangle },
  ];

  return (
    <div className="h-full bg-background flex flex-col">
      {/* Header */}
      <div className="bg-primary px-4 pt-2 pb-6">
        <div className="flex items-center justify-between mb-4">
          <div>
            <p className="text-white/70 text-sm">Good morning,</p>
            <h1 className="text-xl font-bold text-white">John Student</h1>
          </div>
          <button
            onClick={() => onNavigate("profile")}
            className="w-10 h-10 bg-white/20 rounded-full flex items-center justify-center"
          >
            <User className="w-5 h-5 text-white" />
          </button>
        </div>

        {/* Search */}
        <div className="relative">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
          <input
            type="text"
            placeholder="Search alerts..."
            className="w-full pl-10 pr-4 py-2.5 bg-white rounded-xl text-sm"
          />
        </div>
      </div>

      {/* Categories */}
      <div className="px-4 py-3 flex gap-2 overflow-x-auto bg-white border-b border-gray-100">
        {categories.map((cat) => (
          <button
            key={cat.id}
            onClick={() => setFilter(cat.id)}
            className={`flex items-center gap-1.5 px-3 py-1.5 rounded-full text-sm font-medium whitespace-nowrap transition ${
              filter === cat.id
                ? "bg-primary text-white"
                : "bg-gray-100 text-foreground"
            }`}
          >
            <cat.icon className="w-4 h-4" />
            {cat.label}
          </button>
        ))}
      </div>

      {/* Alerts List */}
      <div className="flex-1 overflow-auto px-4 py-3 space-y-3">
        <div className="flex items-center justify-between mb-2">
          <h2 className="font-semibold text-foreground">Recent Alerts</h2>
          <button className="text-primary text-sm font-medium flex items-center gap-1">
            <Filter className="w-4 h-4" />
            Filter
          </button>
        </div>

        {filteredAlerts.map((alert) => (
          <motion.div
            key={alert.id}
            initial={{ opacity: 0, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            onClick={() => onNavigate("eventDetails", alert)}
            className="bg-white rounded-xl p-4 shadow-sm border border-gray-100 cursor-pointer hover:shadow-md transition"
          >
            <div className="flex items-start gap-3">
              <div
                className={`w-10 h-10 rounded-xl flex items-center justify-center ${
                  alert.priority === "high"
                    ? "bg-danger/10"
                    : alert.priority === "medium"
                      ? "bg-accent/10"
                      : "bg-secondary/10"
                }`}
              >
                {alert.category === "Emergency" ? (
                  <AlertTriangle
                    className={`w-5 h-5 ${alert.priority === "high" ? "text-danger" : "text-accent"}`}
                  />
                ) : alert.category === "Academic" ? (
                  <BookOpen className="w-5 h-5 text-primary" />
                ) : (
                  <Calendar className="w-5 h-5 text-secondary" />
                )}
              </div>
              <div className="flex-1 min-w-0">
                <div className="flex items-center gap-2 mb-1">
                  <span
                    className={`text-xs px-2 py-0.5 rounded-full ${
                      alert.priority === "high"
                        ? "bg-danger/10 text-danger"
                        : alert.priority === "medium"
                          ? "bg-accent/10 text-accent"
                          : "bg-secondary/10 text-secondary"
                    }`}
                  >
                    {alert.category}
                  </span>
                </div>
                <h3 className="font-semibold text-foreground text-sm mb-1 truncate">
                  {alert.title}
                </h3>
                <p className="text-muted text-xs line-clamp-2">
                  {alert.description}
                </p>
                <div className="flex items-center gap-3 mt-2 text-xs text-muted">
                  <span className="flex items-center gap-1">
                    <Clock className="w-3 h-3" />
                    {alert.date}
                  </span>
                  <span className="flex items-center gap-1">
                    <MapPin className="w-3 h-3" />
                    {alert.location}
                  </span>
                </div>
              </div>
              <ChevronRight className="w-5 h-5 text-gray-300 flex-shrink-0" />
            </div>
          </motion.div>
        ))}
      </div>

      {/* Bottom Navigation */}
      <BottomNav currentScreen="studentDashboard" onNavigate={onNavigate} />
    </div>
  );
}

// Admin Dashboard
function AdminDashboard({
  onNavigate,
  alerts,
}: {
  onNavigate: (screen: Screen) => void;
  alerts: Alert[];
}) {
  const stats = [
    { label: "Total Alerts", value: alerts.length, icon: Bell, color: "primary" },
    { label: "Active Students", value: "2,458", icon: Users, color: "secondary" },
    { label: "This Week", value: "12", icon: Calendar, color: "accent" },
    { label: "Engagement", value: "89%", icon: BarChart3, color: "primary" },
  ];

  return (
    <div className="h-full bg-background flex flex-col">
      {/* Header */}
      <div className="bg-gradient-to-r from-secondary to-secondary/80 px-4 pt-2 pb-6">
        <div className="flex items-center justify-between mb-4">
          <div>
            <p className="text-white/70 text-sm">Admin Dashboard</p>
            <h1 className="text-xl font-bold text-white">Dr. Smith</h1>
          </div>
          <div className="flex items-center gap-2">
            <button className="w-10 h-10 bg-white/20 rounded-full flex items-center justify-center">
              <Bell className="w-5 h-5 text-white" />
            </button>
            <button
              onClick={() => onNavigate("profile")}
              className="w-10 h-10 bg-white/20 rounded-full flex items-center justify-center"
            >
              <User className="w-5 h-5 text-white" />
            </button>
          </div>
        </div>

        {/* Admin Badge */}
        <div className="flex items-center gap-2 bg-white/10 rounded-lg px-3 py-2">
          <Shield className="w-4 h-4 text-white" />
          <span className="text-white text-sm font-medium">
            Administrator Access
          </span>
        </div>
      </div>

      {/* Stats Grid */}
      <div className="px-4 -mt-3">
        <div className="grid grid-cols-2 gap-3">
          {stats.map((stat, i) => (
            <motion.div
              key={stat.label}
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: i * 0.1 }}
              className="bg-white rounded-xl p-4 shadow-sm"
            >
              <div
                className={`w-8 h-8 rounded-lg flex items-center justify-center mb-2 ${
                  stat.color === "primary"
                    ? "bg-primary/10"
                    : stat.color === "secondary"
                      ? "bg-secondary/10"
                      : "bg-accent/10"
                }`}
              >
                <stat.icon
                  className={`w-4 h-4 ${
                    stat.color === "primary"
                      ? "text-primary"
                      : stat.color === "secondary"
                        ? "text-secondary"
                        : "text-accent"
                  }`}
                />
              </div>
              <p className="text-xl font-bold text-foreground">{stat.value}</p>
              <p className="text-xs text-muted">{stat.label}</p>
            </motion.div>
          ))}
        </div>
      </div>

      {/* Quick Actions */}
      <div className="px-4 py-4">
        <h2 className="font-semibold text-foreground mb-3">Quick Actions</h2>
        <div className="grid grid-cols-2 gap-3">
          <button
            onClick={() => onNavigate("createAlert")}
            className="flex items-center gap-3 bg-primary text-white rounded-xl p-4 shadow-lg shadow-primary/25"
          >
            <Plus className="w-5 h-5" />
            <span className="font-medium text-sm">Create Alert</span>
          </button>
          <button className="flex items-center gap-3 bg-danger text-white rounded-xl p-4 shadow-lg shadow-danger/25">
            <Megaphone className="w-5 h-5" />
            <span className="font-medium text-sm">Emergency</span>
          </button>
        </div>
      </div>

      {/* Recent Activity */}
      <div className="flex-1 px-4 overflow-auto pb-20">
        <h2 className="font-semibold text-foreground mb-3">Recent Alerts</h2>
        <div className="space-y-3">
          {alerts.slice(0, 3).map((alert) => (
            <div
              key={alert.id}
              className="bg-white rounded-xl p-3 shadow-sm flex items-center gap-3"
            >
              <div
                className={`w-2 h-2 rounded-full ${
                  alert.priority === "high"
                    ? "bg-danger"
                    : alert.priority === "medium"
                      ? "bg-accent"
                      : "bg-secondary"
                }`}
              />
              <div className="flex-1 min-w-0">
                <p className="font-medium text-sm text-foreground truncate">
                  {alert.title}
                </p>
                <p className="text-xs text-muted">{alert.date}</p>
              </div>
              <div className="flex items-center gap-1 text-xs text-muted">
                <Eye className="w-3 h-3" />
                <span>1.2k</span>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* FAB */}
      <motion.button
        onClick={() => onNavigate("createAlert")}
        whileHover={{ scale: 1.1 }}
        whileTap={{ scale: 0.9 }}
        className="absolute bottom-24 right-6 w-14 h-14 bg-primary text-white rounded-full shadow-lg shadow-primary/30 flex items-center justify-center"
      >
        <Plus className="w-6 h-6" />
      </motion.button>

      {/* Bottom Navigation */}
      <BottomNav
        currentScreen="adminDashboard"
        onNavigate={onNavigate}
        isAdmin
      />
    </div>
  );
}

// Create Alert Screen (Admin)
function CreateAlertScreen({ onBack, onSend }: { onBack: () => void; onSend: () => void }) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [category, setCategory] = useState("announcement");
  const [priority, setPriority] = useState("medium");
  const [targetSchool, setTargetSchool] = useState("all");

  return (
    <div className="h-full bg-white flex flex-col">
      <div className="px-4 py-3 flex items-center border-b border-gray-100">
        <button onClick={onBack} className="p-2 -ml-2">
          <ChevronRight className="w-5 h-5 rotate-180 text-foreground" />
        </button>
        <h1 className="text-lg font-semibold flex-1 text-center">
          Create Alert
        </h1>
        <button
          onClick={onSend}
          className="px-4 py-1.5 bg-primary text-white rounded-lg text-sm font-medium"
        >
          Send
        </button>
      </div>

      <div className="flex-1 overflow-auto px-4 py-4 space-y-4">
        <div>
          <label className="text-sm font-medium text-foreground block mb-1.5">
            Alert Title
          </label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            placeholder="Enter alert title"
            className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-primary focus:ring-2 focus:ring-primary/20 outline-none transition text-sm"
          />
        </div>

        <div>
          <label className="text-sm font-medium text-foreground block mb-1.5">
            Description
          </label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="Enter alert description"
            rows={4}
            className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-primary focus:ring-2 focus:ring-primary/20 outline-none transition text-sm resize-none"
          />
        </div>

        <div className="grid grid-cols-2 gap-3">
          <div>
            <label className="text-sm font-medium text-foreground block mb-1.5">
              Category
            </label>
            <select
              value={category}
              onChange={(e) => setCategory(e.target.value)}
              className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-primary outline-none text-sm bg-white"
            >
              <option value="announcement">Announcement</option>
              <option value="academic">Academic</option>
              <option value="event">Event</option>
              <option value="emergency">Emergency</option>
            </select>
          </div>
          <div>
            <label className="text-sm font-medium text-foreground block mb-1.5">
              Priority
            </label>
            <select
              value={priority}
              onChange={(e) => setPriority(e.target.value)}
              className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-primary outline-none text-sm bg-white"
            >
              <option value="low">Low</option>
              <option value="medium">Medium</option>
              <option value="high">High</option>
            </select>
          </div>
        </div>

        <div>
          <label className="text-sm font-medium text-foreground block mb-1.5">
            Target Audience
          </label>
          <select
            value={targetSchool}
            onChange={(e) => setTargetSchool(e.target.value)}
            className="w-full px-4 py-3 rounded-xl border border-gray-200 focus:border-primary outline-none text-sm bg-white"
          >
            <option value="all">All Schools</option>
            <option value="engineering">School of Engineering</option>
            <option value="business">School of Business</option>
            <option value="arts">School of Arts & Sciences</option>
            <option value="medicine">School of Medicine</option>
          </select>
        </div>

        {/* Media Attachments */}
        <div>
          <label className="text-sm font-medium text-foreground block mb-2">
            Attachments
          </label>
          <div className="flex gap-2">
            <button className="flex-1 flex items-center justify-center gap-2 py-3 border border-dashed border-gray-300 rounded-xl text-muted hover:border-primary hover:text-primary transition">
              <ImageIcon className="w-5 h-5" />
              <span className="text-sm">Image</span>
            </button>
            <button className="flex-1 flex items-center justify-center gap-2 py-3 border border-dashed border-gray-300 rounded-xl text-muted hover:border-primary hover:text-primary transition">
              <Video className="w-5 h-5" />
              <span className="text-sm">Video</span>
            </button>
            <button className="flex-1 flex items-center justify-center gap-2 py-3 border border-dashed border-gray-300 rounded-xl text-muted hover:border-primary hover:text-primary transition">
              <Mic className="w-5 h-5" />
              <span className="text-sm">Audio</span>
            </button>
          </div>
        </div>

        {/* Location */}
        <div>
          <label className="text-sm font-medium text-foreground block mb-1.5">
            Location (Optional)
          </label>
          <div className="relative">
            <MapPin className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
            <input
              type="text"
              placeholder="Add location"
              className="w-full pl-10 pr-4 py-3 rounded-xl border border-gray-200 focus:border-primary outline-none text-sm"
            />
          </div>
        </div>

        {/* Schedule */}
        <div className="flex items-center justify-between p-4 bg-gray-50 rounded-xl">
          <div className="flex items-center gap-3">
            <Clock className="w-5 h-5 text-muted" />
            <div>
              <p className="text-sm font-medium text-foreground">
                Schedule for later
              </p>
              <p className="text-xs text-muted">Send at a specific time</p>
            </div>
          </div>
          <div className="w-12 h-6 bg-gray-200 rounded-full relative">
            <div className="w-5 h-5 bg-white rounded-full absolute left-0.5 top-0.5 shadow" />
          </div>
        </div>
      </div>
    </div>
  );
}

// Event Details Screen
function EventDetailsScreen({
  alert,
  onBack,
}: {
  alert: Alert;
  onBack: () => void;
}) {
  return (
    <div className="h-full bg-white flex flex-col">
      <div className="px-4 py-3 flex items-center border-b border-gray-100">
        <button onClick={onBack} className="p-2 -ml-2">
          <ChevronRight className="w-5 h-5 rotate-180 text-foreground" />
        </button>
        <h1 className="text-lg font-semibold flex-1 text-center pr-7">
          Alert Details
        </h1>
      </div>

      <div className="flex-1 overflow-auto">
        {/* Header Image Placeholder */}
        <div className="h-48 bg-gradient-to-br from-primary/20 to-primary/5 flex items-center justify-center">
          {alert.category === "Emergency" ? (
            <AlertTriangle className="w-16 h-16 text-danger" />
          ) : alert.category === "Academic" ? (
            <BookOpen className="w-16 h-16 text-primary" />
          ) : (
            <Calendar className="w-16 h-16 text-secondary" />
          )}
        </div>

        <div className="px-4 py-4">
          <div className="flex items-center gap-2 mb-3">
            <span
              className={`text-xs px-3 py-1 rounded-full ${
                alert.priority === "high"
                  ? "bg-danger/10 text-danger"
                  : alert.priority === "medium"
                    ? "bg-accent/10 text-accent"
                    : "bg-secondary/10 text-secondary"
              }`}
            >
              {alert.priority.charAt(0).toUpperCase() + alert.priority.slice(1)}{" "}
              Priority
            </span>
            <span className="text-xs px-3 py-1 rounded-full bg-gray-100 text-foreground">
              {alert.category}
            </span>
          </div>

          <h1 className="text-xl font-bold text-foreground mb-3">
            {alert.title}
          </h1>

          <p className="text-muted text-sm leading-relaxed mb-6">
            {alert.description}
          </p>

          {/* Details */}
          <div className="space-y-3 mb-6">
            <div className="flex items-center gap-3 p-3 bg-gray-50 rounded-xl">
              <Calendar className="w-5 h-5 text-primary" />
              <div>
                <p className="text-xs text-muted">Date</p>
                <p className="text-sm font-medium text-foreground">
                  {alert.date}
                </p>
              </div>
            </div>
            <div className="flex items-center gap-3 p-3 bg-gray-50 rounded-xl">
              <Clock className="w-5 h-5 text-primary" />
              <div>
                <p className="text-xs text-muted">Time</p>
                <p className="text-sm font-medium text-foreground">
                  {alert.time}
                </p>
              </div>
            </div>
            <div className="flex items-center gap-3 p-3 bg-gray-50 rounded-xl">
              <MapPin className="w-5 h-5 text-primary" />
              <div>
                <p className="text-xs text-muted">Location</p>
                <p className="text-sm font-medium text-foreground">
                  {alert.location}
                </p>
              </div>
            </div>
          </div>

          {/* Map Preview */}
          <div className="h-40 bg-gray-100 rounded-xl flex items-center justify-center mb-6">
            <div className="text-center">
              <MapPin className="w-8 h-8 text-primary mx-auto mb-2" />
              <p className="text-sm text-muted">Map View</p>
              <p className="text-xs text-primary">Tap to open in Maps</p>
            </div>
          </div>

          {/* Actions */}
          <div className="flex gap-3">
            <button className="flex-1 py-3 bg-primary text-white rounded-xl font-medium flex items-center justify-center gap-2">
              <Bell className="w-5 h-5" />
              Set Reminder
            </button>
            <button className="px-4 py-3 bg-gray-100 text-foreground rounded-xl">
              <Send className="w-5 h-5" />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

// Profile Screen
function ProfileScreen({
  onBack,
  onLogout,
  isAdmin,
}: {
  onBack: () => void;
  onLogout: () => void;
  isAdmin: boolean;
}) {
  return (
    <div className="h-full bg-white flex flex-col">
      <div className="px-4 py-3 flex items-center border-b border-gray-100">
        <button onClick={onBack} className="p-2 -ml-2">
          <ChevronRight className="w-5 h-5 rotate-180 text-foreground" />
        </button>
        <h1 className="text-lg font-semibold flex-1 text-center pr-7">
          Profile
        </h1>
      </div>

      <div className="flex-1 overflow-auto px-4 py-6">
        {/* Profile Header */}
        <div className="text-center mb-6">
          <div className="w-20 h-20 bg-primary/10 rounded-full flex items-center justify-center mx-auto mb-3">
            <User className="w-10 h-10 text-primary" />
          </div>
          <h2 className="text-xl font-bold text-foreground">
            {isAdmin ? "Dr. Smith" : "John Student"}
          </h2>
          <p className="text-muted text-sm">
            {isAdmin ? "admin@college.edu" : "john.student@college.edu"}
          </p>
          {isAdmin && (
            <span className="inline-flex items-center gap-1 mt-2 px-3 py-1 bg-secondary/10 text-secondary rounded-full text-xs font-medium">
              <Shield className="w-3 h-3" />
              Administrator
            </span>
          )}
        </div>

        {/* Settings */}
        <div className="space-y-2">
          <h3 className="text-sm font-semibold text-muted mb-3">
            Notification Preferences
          </h3>

          {["Academic Alerts", "Event Updates", "Emergency Alerts"].map(
            (item) => (
              <div
                key={item}
                className="flex items-center justify-between p-4 bg-gray-50 rounded-xl"
              >
                <span className="text-sm font-medium text-foreground">
                  {item}
                </span>
                <div className="w-12 h-6 bg-primary rounded-full relative">
                  <div className="w-5 h-5 bg-white rounded-full absolute right-0.5 top-0.5 shadow" />
                </div>
              </div>
            )
          )}
        </div>

        {/* Account Settings */}
        <div className="mt-6 space-y-2">
          <h3 className="text-sm font-semibold text-muted mb-3">Account</h3>

          {[
            { label: "Edit Profile", icon: User },
            { label: "Change Password", icon: Shield },
            { label: "App Settings", icon: Settings },
          ].map((item) => (
            <button
              key={item.label}
              className="flex items-center justify-between w-full p-4 bg-gray-50 rounded-xl"
            >
              <div className="flex items-center gap-3">
                <item.icon className="w-5 h-5 text-muted" />
                <span className="text-sm font-medium text-foreground">
                  {item.label}
                </span>
              </div>
              <ChevronRight className="w-5 h-5 text-gray-300" />
            </button>
          ))}
        </div>

        {/* Logout */}
        <button
          onClick={onLogout}
          className="w-full mt-6 py-3 bg-danger/10 text-danger rounded-xl font-medium flex items-center justify-center gap-2"
        >
          <LogOut className="w-5 h-5" />
          Sign Out
        </button>
      </div>
    </div>
  );
}

// Alerts Stream Screen
function AlertsScreen({
  onNavigate,
  alerts,
}: {
  onNavigate: (screen: Screen, data?: Alert) => void;
  alerts: Alert[];
}) {
  return (
    <div className="h-full bg-background flex flex-col">
      <div className="bg-white px-4 py-3 border-b border-gray-100">
        <h1 className="text-lg font-semibold text-foreground">All Alerts</h1>
        <p className="text-sm text-muted">Stay updated with latest news</p>
      </div>

      <div className="flex-1 overflow-auto px-4 py-3 space-y-3">
        {alerts.map((alert, i) => (
          <motion.div
            key={alert.id}
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ delay: i * 0.1 }}
            onClick={() => onNavigate("eventDetails", alert)}
            className="bg-white rounded-xl p-4 shadow-sm cursor-pointer"
          >
            <div className="flex items-start gap-3">
              <div
                className={`w-3 h-3 rounded-full mt-1.5 ${
                  alert.priority === "high"
                    ? "bg-danger"
                    : alert.priority === "medium"
                      ? "bg-accent"
                      : "bg-secondary"
                }`}
              />
              <div className="flex-1">
                <p className="text-xs text-muted mb-1">{alert.date}</p>
                <h3 className="font-semibold text-foreground text-sm mb-1">
                  {alert.title}
                </h3>
                <p className="text-muted text-xs line-clamp-2">
                  {alert.description}
                </p>
              </div>
            </div>
          </motion.div>
        ))}
      </div>

      <BottomNav currentScreen="alerts" onNavigate={onNavigate} />
    </div>
  );
}

// Discover Screen
function DiscoverScreen({
  onNavigate,
}: {
  onNavigate: (screen: Screen) => void;
}) {
  const categories = [
    { name: "Academic", icon: BookOpen, count: 12, color: "primary" },
    { name: "Sports", icon: Trophy, count: 8, color: "secondary" },
    { name: "Events", icon: Calendar, count: 15, color: "accent" },
    { name: "Clubs", icon: Users, count: 23, color: "primary" },
  ];

  return (
    <div className="h-full bg-background flex flex-col">
      <div className="bg-white px-4 py-3 border-b border-gray-100">
        <h1 className="text-lg font-semibold text-foreground">Discover</h1>
        <p className="text-sm text-muted">Explore campus activities</p>
      </div>

      <div className="flex-1 overflow-auto px-4 py-4">
        {/* Search */}
        <div className="relative mb-4">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
          <input
            type="text"
            placeholder="Search events, clubs..."
            className="w-full pl-10 pr-4 py-3 bg-white rounded-xl text-sm border border-gray-100"
          />
        </div>

        {/* Categories */}
        <h2 className="font-semibold text-foreground mb-3">Categories</h2>
        <div className="grid grid-cols-2 gap-3 mb-6">
          {categories.map((cat) => (
            <div
              key={cat.name}
              className="bg-white rounded-xl p-4 shadow-sm cursor-pointer hover:shadow-md transition"
            >
              <div
                className={`w-10 h-10 rounded-xl flex items-center justify-center mb-3 ${
                  cat.color === "primary"
                    ? "bg-primary/10"
                    : cat.color === "secondary"
                      ? "bg-secondary/10"
                      : "bg-accent/10"
                }`}
              >
                <cat.icon
                  className={`w-5 h-5 ${
                    cat.color === "primary"
                      ? "text-primary"
                      : cat.color === "secondary"
                        ? "text-secondary"
                        : "text-accent"
                  }`}
                />
              </div>
              <h3 className="font-semibold text-foreground text-sm">
                {cat.name}
              </h3>
              <p className="text-xs text-muted">{cat.count} updates</p>
            </div>
          ))}
        </div>

        {/* Upcoming */}
        <h2 className="font-semibold text-foreground mb-3">Upcoming Events</h2>
        <div className="space-y-3">
          {[
            { name: "Spring Concert", date: "May 25", time: "7:00 PM" },
            { name: "Hackathon 2024", date: "June 1-2", time: "All Day" },
            { name: "Guest Lecture", date: "May 22", time: "3:00 PM" },
          ].map((event) => (
            <div
              key={event.name}
              className="bg-white rounded-xl p-4 shadow-sm flex items-center gap-3"
            >
              <div className="w-12 h-12 bg-primary/10 rounded-xl flex items-center justify-center">
                <Calendar className="w-6 h-6 text-primary" />
              </div>
              <div className="flex-1">
                <h3 className="font-semibold text-foreground text-sm">
                  {event.name}
                </h3>
                <p className="text-xs text-muted">
                  {event.date} at {event.time}
                </p>
              </div>
              <ChevronRight className="w-5 h-5 text-gray-300" />
            </div>
          ))}
        </div>
      </div>

      <BottomNav currentScreen="discover" onNavigate={onNavigate} />
    </div>
  );
}

// Bottom Navigation
function BottomNav({
  currentScreen,
  onNavigate,
  isAdmin = false,
}: {
  currentScreen: Screen;
  onNavigate: (screen: Screen) => void;
  isAdmin?: boolean;
}) {
  const items = [
    {
      id: isAdmin ? "adminDashboard" : "studentDashboard",
      icon: Home,
      label: "Home",
    },
    { id: "alerts", icon: Bell, label: "Alerts" },
    { id: "discover", icon: Search, label: "Discover" },
    { id: "profile", icon: User, label: "Profile" },
  ];

  return (
    <div className="bg-white border-t border-gray-100 px-4 py-2 flex items-center justify-around">
      {items.map((item) => (
        <button
          key={item.id}
          onClick={() => onNavigate(item.id as Screen)}
          className={`flex flex-col items-center py-2 px-4 rounded-xl transition ${
            currentScreen === item.id
              ? "text-primary"
              : "text-muted hover:text-foreground"
          }`}
        >
          <item.icon className="w-5 h-5 mb-1" />
          <span className="text-xs font-medium">{item.label}</span>
        </button>
      ))}
    </div>
  );
}

// Notification Toast
function NotificationToast({
  alert,
  onDismiss,
}: {
  alert: Alert;
  onDismiss: () => void;
}) {
  return (
    <motion.div
      initial={{ y: -100, opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      exit={{ y: -100, opacity: 0 }}
      className="absolute top-12 left-3 right-3 bg-white rounded-2xl shadow-xl p-4 z-50 border border-gray-100"
    >
      <div className="flex items-start gap-3">
        <div className="w-10 h-10 bg-primary/10 rounded-full flex items-center justify-center flex-shrink-0">
          <Bell className="w-5 h-5 text-primary" />
        </div>
        <div className="flex-1 min-w-0">
          <p className="text-xs text-muted mb-0.5">College Alert</p>
          <p className="font-semibold text-foreground text-sm truncate">
            {alert.title}
          </p>
          <p className="text-xs text-muted truncate">{alert.description}</p>
        </div>
        <button onClick={onDismiss} className="p-1">
          <X className="w-4 h-4 text-muted" />
        </button>
      </div>
    </motion.div>
  );
}

// Main App Component
export default function DemoApp() {
  const [screen, setScreen] = useState<Screen>("splash");
  const [userRole, setUserRole] = useState<UserRole | null>(null);
  const [alerts, setAlerts] = useState<Alert[]>(sampleAlerts);
  const [selectedAlert, setSelectedAlert] = useState<Alert | null>(null);
  const [showNotification, setShowNotification] = useState(false);
  const [notification, setNotification] = useState<Alert | null>(null);

  const handleNavigate = (newScreen: Screen, data?: Alert) => {
    if (data) {
      setSelectedAlert(data);
    }
    setScreen(newScreen);
  };

  const handleLogin = (role: UserRole) => {
    setUserRole(role);
    setScreen(role === "admin" ? "adminDashboard" : "studentDashboard");
  };

  const handleLogout = () => {
    setUserRole(null);
    setScreen("login");
  };

  const handleSendAlert = () => {
    const newAlert: Alert = {
      id: String(alerts.length + 1),
      title: "New Campus Update",
      description:
        "A new alert has been created and sent to all students. Check the details for more information.",
      category: "Announcement",
      date: "Today",
      time: "Just now",
      location: "Campus Wide",
      priority: "medium",
    };
    setAlerts([newAlert, ...alerts]);
    setNotification(newAlert);
    setShowNotification(true);
    setTimeout(() => setShowNotification(false), 4000);
    setScreen(userRole === "admin" ? "adminDashboard" : "studentDashboard");
  };

  const renderScreen = () => {
    switch (screen) {
      case "splash":
        return <SplashScreen onComplete={() => setScreen("login")} />;
      case "login":
        return (
          <LoginScreen
            onLogin={handleLogin}
            onSignUp={() => setScreen("signup")}
          />
        );
      case "signup":
        return (
          <SignUpScreen
            onSignUp={handleLogin}
            onBack={() => setScreen("login")}
          />
        );
      case "studentDashboard":
        return <StudentDashboard onNavigate={handleNavigate} alerts={alerts} />;
      case "adminDashboard":
        return <AdminDashboard onNavigate={handleNavigate} alerts={alerts} />;
      case "createAlert":
        return (
          <CreateAlertScreen
            onBack={() =>
              setScreen(
                userRole === "admin" ? "adminDashboard" : "studentDashboard"
              )
            }
            onSend={handleSendAlert}
          />
        );
      case "eventDetails":
        return selectedAlert ? (
          <EventDetailsScreen
            alert={selectedAlert}
            onBack={() =>
              setScreen(
                userRole === "admin" ? "adminDashboard" : "studentDashboard"
              )
            }
          />
        ) : null;
      case "profile":
        return (
          <ProfileScreen
            onBack={() =>
              setScreen(
                userRole === "admin" ? "adminDashboard" : "studentDashboard"
              )
            }
            onLogout={handleLogout}
            isAdmin={userRole === "admin"}
          />
        );
      case "alerts":
        return <AlertsScreen onNavigate={handleNavigate} alerts={alerts} />;
      case "discover":
        return <DiscoverScreen onNavigate={handleNavigate} />;
      default:
        return null;
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-100 to-gray-200 flex items-center justify-center p-8">
      <div className="text-center max-w-md mr-12 hidden lg:block">
        <h1 className="text-4xl font-bold text-foreground mb-4">
          College Alert
        </h1>
        <p className="text-lg text-muted mb-6">
          Interactive App Demo
        </p>
        <div className="space-y-3 text-left bg-white rounded-2xl p-6 shadow-sm">
          <h3 className="font-semibold text-foreground mb-3">Demo Flow:</h3>
          <div className="flex items-center gap-3 text-sm">
            <CheckCircle className="w-5 h-5 text-secondary" />
            <span>Login as Student or Admin</span>
          </div>
          <div className="flex items-center gap-3 text-sm">
            <CheckCircle className="w-5 h-5 text-secondary" />
            <span>Browse alerts and notifications</span>
          </div>
          <div className="flex items-center gap-3 text-sm">
            <CheckCircle className="w-5 h-5 text-secondary" />
            <span>Admin: Create and send alerts</span>
          </div>
          <div className="flex items-center gap-3 text-sm">
            <CheckCircle className="w-5 h-5 text-secondary" />
            <span>Student: Receive notifications</span>
          </div>
          <div className="flex items-center gap-3 text-sm">
            <CheckCircle className="w-5 h-5 text-secondary" />
            <span>View event details with location</span>
          </div>
        </div>
        <p className="text-sm text-muted mt-6">
          Use any screen recorder to capture this demo
        </p>
      </div>

      <PhoneFrame>
        <AnimatePresence mode="wait">
          {showNotification && notification && (
            <NotificationToast
              alert={notification}
              onDismiss={() => setShowNotification(false)}
            />
          )}
        </AnimatePresence>
        <AnimatePresence mode="wait">
          <motion.div
            key={screen}
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="h-full"
          >
            {renderScreen()}
          </motion.div>
        </AnimatePresence>
      </PhoneFrame>
    </div>
  );
}
