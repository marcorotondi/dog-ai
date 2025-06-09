package com.marco.dogai.entity;

import org.springframework.data.annotation.Id;

public record Dog(@Id int id, String name, String owner, String description) {
}