import React from "react";

export interface LinkType {
  name: string;
  href: string;
  icon: string;
}

export interface ModalCustomProps {
  name: string;
  buttonString?: {
    cancel: string;
    add: string;
  };
  design?: string;
  open: boolean;
  onClose: () => void;
  onClickEvent: () => void;
  children: React.ReactNode;
}

export interface ModalProps {
  open: boolean;
  children: React.ReactNode;
}

export interface hoverProps {
  content: string;
  children: React.ReactNode;
}

export interface Recruit {
  career: [number, number];
  company: string;
  companyId: number;
  dueDate: [number, number, number];
  id: number;
  preferredRequirements: string[];
  qualificationRequirements: string[];
  source: string;
  thumbnailUrl: string;
  title: string;
  url: string;
}
