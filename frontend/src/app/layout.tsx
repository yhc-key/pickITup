"use client";
import type { Metadata } from "next";
import { Inter } from "next/font/google";
const inter = Inter({ subsets: ["latin"] });

import "./globals.css";

// export const metadata: Metadata = {
//   title: "pick IT up",
//   description: "pick your IT information and recruit",
// };

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return <html lang="kr">{children}</html>;
}
