"use client";
import React, { ReactNode } from "react";
import ReactDOM from "react-dom";

interface ModalProps {
  open: boolean;
  onClose: () => void;
  children: ReactNode;
}

export default function Modal({ open, onClose, children }: ModalProps) {
  if (!open) return null;

  return ReactDOM.createPortal(
    <>
      <div className="fixed top-0 left-0 right-0 bottom-0 bg-black bg-opacity-40 z-50" />
      <div className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 p-16a bg-white z-50 rounded-xl">
        {children}
      </div>
    </>,
    document.getElementById("globalModal") as HTMLElement
  );
}
