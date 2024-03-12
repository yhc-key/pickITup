"use client";
import { useState } from "react";

interface hoverProps {
  content: string;
  children: React.ReactNode;
}

export default function Tooltip({ content, children }: hoverProps) {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <>
      <div
        onMouseEnter={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)}
        className="cursor-pointer"
      >
        {children}
      </div>
      <div>
        {isHovered ? (
          <div className="relative">
            <div className="absolute left-1 -top-12 px-5 py-2.5 whitespace-pre bg-f5gray-300 text-f5black-400 rounded-md transition-all">
              {content}
            </div>
          </div>
        ) : (
          <div></div>
        )}
      </div>
    </>
  );
}
